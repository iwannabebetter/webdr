--
-- PostgreSQL database dump
--

-- Started on 2007-10-08 18:59:19

SET client_encoding = 'LATIN1';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1759 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 301 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 262 (class 0 OID 0)
-- Name: chkpass; Type: SHELL TYPE; Schema: public; Owner: webdr
--

CREATE TYPE chkpass;


--
-- TOC entry 18 (class 1255 OID 19607)
-- Dependencies: 4 262
-- Name: chkpass_in(cstring); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION chkpass_in(cstring) RETURNS chkpass
    AS '$libdir/chkpass', 'chkpass_in'
    LANGUAGE c STRICT;


ALTER FUNCTION public.chkpass_in(cstring) OWNER TO webdr;

--
-- TOC entry 19 (class 1255 OID 19608)
-- Dependencies: 4 262
-- Name: chkpass_out(chkpass); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION chkpass_out(chkpass) RETURNS cstring
    AS '$libdir/chkpass', 'chkpass_out'
    LANGUAGE c STRICT;


ALTER FUNCTION public.chkpass_out(chkpass) OWNER TO webdr;

--
-- TOC entry 261 (class 1247 OID 19606)
-- Dependencies: 18 4 19
-- Name: chkpass; Type: TYPE; Schema: public; Owner: webdr
--

CREATE TYPE chkpass (
    INTERNALLENGTH = 16,
    INPUT = chkpass_in,
    OUTPUT = chkpass_out,
    ALIGNMENT = int4,
    STORAGE = plain
);


ALTER TYPE public.chkpass OWNER TO webdr;

--
-- TOC entry 1761 (class 0 OID 0)
-- Dependencies: 261
-- Name: TYPE chkpass; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TYPE chkpass IS 'password type with checks';


--
-- TOC entry 27 (class 1255 OID 19956)
-- Dependencies: 4 301
-- Name: coincide_horario(time without time zone, time without time zone, time without time zone, time without time zone); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) RETURNS boolean
    AS 'BEGIN
  IF (ini1 >= ini2) AND (ini1 <= fin2) THEN
    RETURN true;
  ELSIF (fin1 <= fin2) AND (fin1 >= ini1) THEN
    RETURN true;
  ELSE
    RETURN false;
  END IF;
END;'
    LANGUAGE plpgsql;


ALTER FUNCTION public.coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) OWNER TO webdr;

--
-- TOC entry 1762 (class 0 OID 0)
-- Dependencies: 27
-- Name: FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) IS 'Verifica si dos horarios se solapan.';


--
-- TOC entry 24 (class 1255 OID 19950)
-- Dependencies: 301 4
-- Name: dias_coincidentes(); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION dias_coincidentes() RETURNS "trigger"
    AS 'DECLARE
  v_dia_ingresado INTEGER;
  v_horario BIGINT;
  v_dia INTEGER;
BEGIN
  SELECT cod_horario INTO v_horario FROM turno WHERE cod_turno = NEW.cod_turno;
  SELECT dia INTO v_dia FROM horario WHERE cod_horario = v_horario;

  SELECT EXTRACT(dow FROM NEW.fecha_reservada) INTO v_dia_ingresado;

  IF (v_dia <> v_dia_ingresado) THEN
    RAISE EXCEPTION ''El dia de la fecha reservada no coincide con el dia del turno relacionado'';
    RETURN NULL;
  END IF;
  RETURN NEW;
END;'
    LANGUAGE plpgsql;


ALTER FUNCTION public.dias_coincidentes() OWNER TO webdr;

--
-- TOC entry 1763 (class 0 OID 0)
-- Dependencies: 24
-- Name: FUNCTION dias_coincidentes(); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION dias_coincidentes() IS 'Verifica que el dia de la fecha ingresada para la fecha de reserva, coincida con el dia del turno relacionado.';


--
-- TOC entry 21 (class 1255 OID 19611)
-- Dependencies: 4 261
-- Name: eq(chkpass, text); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION eq(chkpass, text) RETURNS boolean
    AS '$libdir/chkpass', 'chkpass_eq'
    LANGUAGE c STRICT;


ALTER FUNCTION public.eq(chkpass, text) OWNER TO webdr;

--
-- TOC entry 23 (class 1255 OID 19948)
-- Dependencies: 301 4
-- Name: hora_en_rango(); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION hora_en_rango() RETURNS "trigger"
    AS 'DECLARE
  hora_ini time;
  hora_fin time;
BEGIN
  SELECT hora_inicio INTO hora_ini FROM horario WHERE cod_horario = NEW.cod_horario;
  SELECT hora_fin    INTO hora_fin FROM horario WHERE cod_horario = NEW.cod_horario;

  IF (NEW.hora < hora_ini) OR (NEW.hora > hora_fin) THEN
    RAISE EXCEPTION ''La hora del turno debe estar dentro del horario relacionado'';
    RETURN NULL;
  END IF;
  RETURN NEW;
END;'
    LANGUAGE plpgsql;


ALTER FUNCTION public.hora_en_rango() OWNER TO webdr;

--
-- TOC entry 1764 (class 0 OID 0)
-- Dependencies: 23
-- Name: FUNCTION hora_en_rango(); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION hora_en_rango() IS 'Verifica que la hora de un turno esté dentro del rango de horas del horario relacionado.';


--
-- TOC entry 26 (class 1255 OID 19955)
-- Dependencies: 4 301
-- Name: horarios_no_solapados(); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION horarios_no_solapados() RETURNS "trigger"
    AS 'DECLARE
  v_horario RECORD;
BEGIN
  FOR v_horario IN SELECT * FROM horario WHERE cod_doctor = NEW.cod_doctor LOOP
    IF (NEW.dia = v_horario.dia) AND coincide_horario(NEW.hora_inicio, NEW.hora_fin, v_horario.hora_inicio, v_horario.hora_fin) THEN
      RAISE EXCEPTION ''Hay un solapamiento de horarios'';
      RETURN NULL;
    END IF;
  END LOOP;
  RETURN NEW;
END;'
    LANGUAGE plpgsql;


ALTER FUNCTION public.horarios_no_solapados() OWNER TO webdr;

--
-- TOC entry 1765 (class 0 OID 0)
-- Dependencies: 26
-- Name: FUNCTION horarios_no_solapados(); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION horarios_no_solapados() IS 'Verifica que dos horarios para un mismo doctor no estén solapados.';


--
-- TOC entry 22 (class 1255 OID 19612)
-- Dependencies: 4 261
-- Name: ne(chkpass, text); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION ne(chkpass, text) RETURNS boolean
    AS '$libdir/chkpass', 'chkpass_ne'
    LANGUAGE c STRICT;


ALTER FUNCTION public.ne(chkpass, text) OWNER TO webdr;

--
-- TOC entry 20 (class 1255 OID 19610)
-- Dependencies: 261 4
-- Name: raw(chkpass); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION raw(chkpass) RETURNS text
    AS '$libdir/chkpass', 'chkpass_rout'
    LANGUAGE c STRICT;


ALTER FUNCTION public.raw(chkpass) OWNER TO webdr;

--
-- TOC entry 969 (class 2617 OID 19615)
-- Dependencies: 261 4 22
-- Name: <>; Type: OPERATOR; Schema: public; Owner: webdr
--

CREATE OPERATOR <> (
    PROCEDURE = ne,
    LEFTARG = chkpass,
    RIGHTARG = text,
    NEGATOR = =
);


ALTER OPERATOR public.<> (chkpass, text) OWNER TO webdr;

--
-- TOC entry 970 (class 2617 OID 19614)
-- Dependencies: 4 21 261
-- Name: =; Type: OPERATOR; Schema: public; Owner: webdr
--

CREATE OPERATOR = (
    PROCEDURE = eq,
    LEFTARG = chkpass,
    RIGHTARG = text,
    COMMUTATOR = =,
    NEGATOR = <>
);


ALTER OPERATOR public.= (chkpass, text) OWNER TO webdr;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1317 (class 1259 OID 19805)
-- Dependencies: 4
-- Name: administrador; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE administrador (
    idadministrador bigint NOT NULL
);


ALTER TABLE public.administrador OWNER TO webdr;

--
-- TOC entry 1325 (class 1259 OID 19868)
-- Dependencies: 1676 1677 4
-- Name: consulta; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE consulta (
    idconsulta bigint NOT NULL,
    fecha date NOT NULL,
    horainicio time without time zone,
    horafin time without time zone,
    idpaciente bigint NOT NULL,
    iddoctor bigint NOT NULL,
    CONSTRAINT consulta_check CHECK ((horainicio < horafin)),
    CONSTRAINT consulta_fecha_check CHECK ((fecha <= ('now'::text)::date))
);


ALTER TABLE public.consulta OWNER TO webdr;

--
-- TOC entry 1324 (class 1259 OID 19866)
-- Dependencies: 1325 4
-- Name: consulta_idconsulta_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE consulta_idconsulta_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.consulta_idconsulta_seq OWNER TO webdr;

--
-- TOC entry 1766 (class 0 OID 0)
-- Dependencies: 1324
-- Name: consulta_idconsulta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE consulta_idconsulta_seq OWNED BY consulta.idconsulta;


--
-- TOC entry 1767 (class 0 OID 0)
-- Dependencies: 1324
-- Name: consulta_idconsulta_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('consulta_idconsulta_seq', 1, false);


--
-- TOC entry 1316 (class 1259 OID 19796)
-- Dependencies: 4
-- Name: doctor; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE doctor (
    iddoctor bigint NOT NULL
);


ALTER TABLE public.doctor OWNER TO webdr;

--
-- TOC entry 1321 (class 1259 OID 19840)
-- Dependencies: 4
-- Name: doctor_especialidad; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE doctor_especialidad (
    iddoctor bigint NOT NULL,
    idespecialidad bigint NOT NULL
);


ALTER TABLE public.doctor_especialidad OWNER TO webdr;

--
-- TOC entry 1312 (class 1259 OID 19770)
-- Dependencies: 4
-- Name: especialidad; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE especialidad (
    idespecialidad bigint NOT NULL,
    nomespecialidad character varying(50) NOT NULL,
    descripcion text
);


ALTER TABLE public.especialidad OWNER TO webdr;

--
-- TOC entry 1311 (class 1259 OID 19768)
-- Dependencies: 1312 4
-- Name: especialidad_idespecialidad_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE especialidad_idespecialidad_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.especialidad_idespecialidad_seq OWNER TO webdr;

--
-- TOC entry 1768 (class 0 OID 0)
-- Dependencies: 1311
-- Name: especialidad_idespecialidad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE especialidad_idespecialidad_seq OWNED BY especialidad.idespecialidad;


--
-- TOC entry 1769 (class 0 OID 0)
-- Dependencies: 1311
-- Name: especialidad_idespecialidad_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('especialidad_idespecialidad_seq', 1, false);


--
-- TOC entry 1319 (class 1259 OID 19816)
-- Dependencies: 1671 1672 4
-- Name: horario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE horario (
    idhorario bigint NOT NULL,
    horainicio time without time zone NOT NULL,
    horafin time without time zone NOT NULL,
    iddoctor bigint NOT NULL,
    dia integer NOT NULL,
    CONSTRAINT horario_check CHECK ((horainicio < horafin)),
    CONSTRAINT horario_dia_check CHECK (((dia >= 0) AND (dia <= 6)))
);


ALTER TABLE public.horario OWNER TO webdr;

--
-- TOC entry 1770 (class 0 OID 0)
-- Dependencies: 1319
-- Name: CONSTRAINT horario_dia_check ON horario; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON CONSTRAINT horario_dia_check ON horario IS 'El 0 corresponde a Domingo.';


--
-- TOC entry 1318 (class 1259 OID 19814)
-- Dependencies: 1319 4
-- Name: horario_idhorario_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE horario_idhorario_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.horario_idhorario_seq OWNER TO webdr;

--
-- TOC entry 1771 (class 0 OID 0)
-- Dependencies: 1318
-- Name: horario_idhorario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE horario_idhorario_seq OWNED BY horario.idhorario;


--
-- TOC entry 1772 (class 0 OID 0)
-- Dependencies: 1318
-- Name: horario_idhorario_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('horario_idhorario_seq', 1, false);


--
-- TOC entry 1329 (class 1259 OID 19906)
-- Dependencies: 1682 1683 1684 1685 4
-- Name: notas; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE notas (
    idnotas bigint NOT NULL,
    idconsulta bigint NOT NULL,
    sintomas text NOT NULL,
    diagnostico text NOT NULL,
    indicaciones text NOT NULL,
    recetario text,
    peso real,
    altura integer,
    edad integer,
    unidadedad character(1),
    CONSTRAINT notas_altura_check CHECK ((altura > 0)),
    CONSTRAINT notas_edad_check CHECK ((edad > 0)),
    CONSTRAINT notas_peso_check CHECK ((peso > (0)::double precision)),
    CONSTRAINT notas_unidad_edad_check CHECK ((unidadedad = ANY (ARRAY['A'::bpchar, 'M'::bpchar])))
);


ALTER TABLE public.notas OWNER TO webdr;

--
-- TOC entry 1773 (class 0 OID 0)
-- Dependencies: 1329
-- Name: COLUMN notas.altura; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.altura IS 'En centímetros';


--
-- TOC entry 1774 (class 0 OID 0)
-- Dependencies: 1329
-- Name: COLUMN notas.edad; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.edad IS 'En meses o años';


--
-- TOC entry 1775 (class 0 OID 0)
-- Dependencies: 1329
-- Name: COLUMN notas.unidadedad; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.unidadedad IS 'En meses o años';


--
-- TOC entry 1328 (class 1259 OID 19904)
-- Dependencies: 4 1329
-- Name: notas_idnotas_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE notas_idnotas_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.notas_idnotas_seq OWNER TO webdr;

--
-- TOC entry 1776 (class 0 OID 0)
-- Dependencies: 1328
-- Name: notas_idnotas_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE notas_idnotas_seq OWNED BY notas.idnotas;


--
-- TOC entry 1777 (class 0 OID 0)
-- Dependencies: 1328
-- Name: notas_idnotas_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('notas_idnotas_seq', 1, false);


--
-- TOC entry 1330 (class 1259 OID 19970)
-- Dependencies: 4
-- Name: objeto; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE objeto (
    idobjeto bigint NOT NULL,
    nomobjeto character varying(20) NOT NULL
);


ALTER TABLE public.objeto OWNER TO webdr;

--
-- TOC entry 1331 (class 1259 OID 19972)
-- Dependencies: 4 1330
-- Name: objeto_idobjeto_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE objeto_idobjeto_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.objeto_idobjeto_seq OWNER TO webdr;

--
-- TOC entry 1778 (class 0 OID 0)
-- Dependencies: 1331
-- Name: objeto_idobjeto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE objeto_idobjeto_seq OWNED BY objeto.idobjeto;


--
-- TOC entry 1779 (class 0 OID 0)
-- Dependencies: 1331
-- Name: objeto_idobjeto_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('objeto_idobjeto_seq', 12, true);


--
-- TOC entry 1320 (class 1259 OID 19826)
-- Dependencies: 1673 4
-- Name: paciente; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE paciente (
    idpaciente bigint NOT NULL,
    tiposangre bigint NOT NULL,
    fechaingreso date NOT NULL,
    CONSTRAINT paciente_fecha_ingreso_check CHECK ((fechaingreso <= ('now'::text)::date))
);


ALTER TABLE public.paciente OWNER TO webdr;

--
-- TOC entry 1332 (class 1259 OID 19982)
-- Dependencies: 4
-- Name: permiso; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE permiso (
    idpermiso bigint NOT NULL,
    idusuario bigint NOT NULL,
    idobjeto bigint NOT NULL,
    crear boolean NOT NULL,
    eliminar boolean NOT NULL,
    modificar boolean NOT NULL,
    listar boolean NOT NULL
);


ALTER TABLE public.permiso OWNER TO webdr;

--
-- TOC entry 1333 (class 1259 OID 19984)
-- Dependencies: 4 1332
-- Name: permiso_idpermiso_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE permiso_idpermiso_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.permiso_idpermiso_seq OWNER TO webdr;

--
-- TOC entry 1780 (class 0 OID 0)
-- Dependencies: 1333
-- Name: permiso_idpermiso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE permiso_idpermiso_seq OWNED BY permiso.idpermiso;


--
-- TOC entry 1781 (class 0 OID 0)
-- Dependencies: 1333
-- Name: permiso_idpermiso_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('permiso_idpermiso_seq', 1, false);


--
-- TOC entry 1327 (class 1259 OID 19885)
-- Dependencies: 1678 1680 4
-- Name: reserva; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE reserva (
    idreserva bigint NOT NULL,
    fechareserva timestamp without time zone NOT NULL,
    fechareservada date NOT NULL,
    cancelado boolean DEFAULT false,
    observcancelado text,
    idpaciente bigint NOT NULL,
    idturno bigint NOT NULL,
    CONSTRAINT reserva_fecha_reserva_check CHECK ((fechareserva <= now()))
);


ALTER TABLE public.reserva OWNER TO webdr;

--
-- TOC entry 1326 (class 1259 OID 19883)
-- Dependencies: 4 1327
-- Name: reserva_idreserva_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE reserva_idreserva_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.reserva_idreserva_seq OWNER TO webdr;

--
-- TOC entry 1782 (class 0 OID 0)
-- Dependencies: 1326
-- Name: reserva_idreserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE reserva_idreserva_seq OWNED BY reserva.idreserva;


--
-- TOC entry 1783 (class 0 OID 0)
-- Dependencies: 1326
-- Name: reserva_idreserva_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('reserva_idreserva_seq', 1, false);


--
-- TOC entry 1315 (class 1259 OID 19787)
-- Dependencies: 4
-- Name: secretario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE secretario (
    idsecretario bigint NOT NULL
);


ALTER TABLE public.secretario OWNER TO webdr;

--
-- TOC entry 1310 (class 1259 OID 19762)
-- Dependencies: 4
-- Name: tiposangre; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE tiposangre (
    idsangre bigint NOT NULL,
    tiposangre character(5) NOT NULL
);


ALTER TABLE public.tiposangre OWNER TO webdr;

--
-- TOC entry 1309 (class 1259 OID 19760)
-- Dependencies: 1310 4
-- Name: tiposangre_idsangre_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE tiposangre_idsangre_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tiposangre_idsangre_seq OWNER TO webdr;

--
-- TOC entry 1784 (class 0 OID 0)
-- Dependencies: 1309
-- Name: tiposangre_idsangre_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE tiposangre_idsangre_seq OWNED BY tiposangre.idsangre;


--
-- TOC entry 1785 (class 0 OID 0)
-- Dependencies: 1309
-- Name: tiposangre_idsangre_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('tiposangre_idsangre_seq', 8, true);


--
-- TOC entry 1323 (class 1259 OID 19856)
-- Dependencies: 4
-- Name: turno; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE turno (
    idturno bigint NOT NULL,
    idhorario bigint NOT NULL,
    hora time without time zone
);


ALTER TABLE public.turno OWNER TO webdr;

--
-- TOC entry 1322 (class 1259 OID 19854)
-- Dependencies: 4 1323
-- Name: turno_idturno_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE turno_idturno_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.turno_idturno_seq OWNER TO webdr;

--
-- TOC entry 1786 (class 0 OID 0)
-- Dependencies: 1322
-- Name: turno_idturno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE turno_idturno_seq OWNED BY turno.idturno;


--
-- TOC entry 1787 (class 0 OID 0)
-- Dependencies: 1322
-- Name: turno_idturno_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('turno_idturno_seq', 1, false);


--
-- TOC entry 1314 (class 1259 OID 19781)
-- Dependencies: 1667 1668 1669 261 4
-- Name: usuario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE usuario (
    idusuario bigint NOT NULL,
    userid character varying(30) NOT NULL,
    passwd chkpass NOT NULL,
    cedula integer NOT NULL,
    nombre character varying(50) NOT NULL,
    apellido character varying(50) NOT NULL,
    genero character(1) NOT NULL,
    direccion character varying(200),
    telefono character(15),
    celular character(15),
    email character varying(50),
    fechanac date,
    CONSTRAINT usuario_cedula_check CHECK ((cedula > 0)),
    CONSTRAINT usuario_fechanac_check CHECK ((fechanac <= ('now'::text)::date)),
    CONSTRAINT usuario_genero_check CHECK ((genero = ANY (ARRAY['M'::bpchar, 'F'::bpchar])))
);


ALTER TABLE public.usuario OWNER TO webdr;

--
-- TOC entry 1313 (class 1259 OID 19779)
-- Dependencies: 1314 4
-- Name: usuario_idusuario_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE usuario_idusuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_idusuario_seq OWNER TO webdr;

--
-- TOC entry 1788 (class 0 OID 0)
-- Dependencies: 1313
-- Name: usuario_idusuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE usuario_idusuario_seq OWNED BY usuario.idusuario;


--
-- TOC entry 1789 (class 0 OID 0)
-- Dependencies: 1313
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('usuario_idusuario_seq', 1, false);


--
-- TOC entry 1675 (class 2604 OID 19992)
-- Dependencies: 1325 1324 1325
-- Name: idconsulta; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE consulta ALTER COLUMN idconsulta SET DEFAULT nextval('consulta_idconsulta_seq'::regclass);


--
-- TOC entry 1665 (class 2604 OID 19993)
-- Dependencies: 1312 1311 1312
-- Name: idespecialidad; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE especialidad ALTER COLUMN idespecialidad SET DEFAULT nextval('especialidad_idespecialidad_seq'::regclass);


--
-- TOC entry 1670 (class 2604 OID 19994)
-- Dependencies: 1319 1318 1319
-- Name: idhorario; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE horario ALTER COLUMN idhorario SET DEFAULT nextval('horario_idhorario_seq'::regclass);


--
-- TOC entry 1681 (class 2604 OID 19995)
-- Dependencies: 1329 1328 1329
-- Name: idnotas; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE notas ALTER COLUMN idnotas SET DEFAULT nextval('notas_idnotas_seq'::regclass);


--
-- TOC entry 1686 (class 2604 OID 19997)
-- Dependencies: 1331 1330
-- Name: idobjeto; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE objeto ALTER COLUMN idobjeto SET DEFAULT nextval('objeto_idobjeto_seq'::regclass);


--
-- TOC entry 1687 (class 2604 OID 19998)
-- Dependencies: 1333 1332
-- Name: idpermiso; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE permiso ALTER COLUMN idpermiso SET DEFAULT nextval('permiso_idpermiso_seq'::regclass);


--
-- TOC entry 1679 (class 2604 OID 19999)
-- Dependencies: 1326 1327 1327
-- Name: idreserva; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE reserva ALTER COLUMN idreserva SET DEFAULT nextval('reserva_idreserva_seq'::regclass);


--
-- TOC entry 1664 (class 2604 OID 20000)
-- Dependencies: 1309 1310 1310
-- Name: idsangre; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE tiposangre ALTER COLUMN idsangre SET DEFAULT nextval('tiposangre_idsangre_seq'::regclass);


--
-- TOC entry 1674 (class 2604 OID 20001)
-- Dependencies: 1322 1323 1323
-- Name: idturno; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE turno ALTER COLUMN idturno SET DEFAULT nextval('turno_idturno_seq'::regclass);


--
-- TOC entry 1666 (class 2604 OID 20002)
-- Dependencies: 1313 1314 1314
-- Name: idusuario; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE usuario ALTER COLUMN idusuario SET DEFAULT nextval('usuario_idusuario_seq'::regclass);


--
-- TOC entry 1746 (class 0 OID 19805)
-- Dependencies: 1317
-- Data for Name: administrador; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1751 (class 0 OID 19868)
-- Dependencies: 1325
-- Data for Name: consulta; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1745 (class 0 OID 19796)
-- Dependencies: 1316
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1749 (class 0 OID 19840)
-- Dependencies: 1321
-- Data for Name: doctor_especialidad; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1742 (class 0 OID 19770)
-- Dependencies: 1312
-- Data for Name: especialidad; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1747 (class 0 OID 19816)
-- Dependencies: 1319
-- Data for Name: horario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1753 (class 0 OID 19906)
-- Dependencies: 1329
-- Data for Name: notas; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1754 (class 0 OID 19970)
-- Dependencies: 1330
-- Data for Name: objeto; Type: TABLE DATA; Schema: public; Owner: webdr
--

INSERT INTO objeto (idobjeto, nomobjeto) VALUES (1, 'Usuario');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (2, 'Paciente');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (3, 'Doctor');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (4, 'Consulta');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (5, 'Especialidad');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (6, 'Horario');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (7, 'Notas');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (8, 'Reserva');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (9, 'Tipo Sangre');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (10, 'Turno');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (11, 'Administrador');
INSERT INTO objeto (idobjeto, nomobjeto) VALUES (12, 'Secretario');


--
-- TOC entry 1748 (class 0 OID 19826)
-- Dependencies: 1320
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1755 (class 0 OID 19982)
-- Dependencies: 1332
-- Data for Name: permiso; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1752 (class 0 OID 19885)
-- Dependencies: 1327
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1744 (class 0 OID 19787)
-- Dependencies: 1315
-- Data for Name: secretario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1741 (class 0 OID 19762)
-- Dependencies: 1310
-- Data for Name: tiposangre; Type: TABLE DATA; Schema: public; Owner: webdr
--

INSERT INTO tiposangre (idsangre, tiposangre) VALUES (1, 'AB+  ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (2, 'AB-  ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (3, 'A+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (4, 'A-   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (5, 'B+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (6, 'B-   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (7, 'O+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (8, 'O-   ');


--
-- TOC entry 1750 (class 0 OID 19856)
-- Dependencies: 1323
-- Data for Name: turno; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1743 (class 0 OID 19781)
-- Dependencies: 1314
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1702 (class 2606 OID 19808)
-- Dependencies: 1317 1317
-- Name: administrador_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (idadministrador);


--
-- TOC entry 1712 (class 2606 OID 19872)
-- Dependencies: 1325 1325
-- Name: consulta_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_pkey PRIMARY KEY (idconsulta);


--
-- TOC entry 1708 (class 2606 OID 19843)
-- Dependencies: 1321 1321 1321
-- Name: doctor_especialidad_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_pkey PRIMARY KEY (iddoctor, idespecialidad);


--
-- TOC entry 1700 (class 2606 OID 19799)
-- Dependencies: 1316 1316
-- Name: doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (iddoctor);


--
-- TOC entry 1693 (class 2606 OID 19777)
-- Dependencies: 1312 1312
-- Name: especialidad_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY especialidad
    ADD CONSTRAINT especialidad_pkey PRIMARY KEY (idespecialidad);


--
-- TOC entry 1704 (class 2606 OID 19820)
-- Dependencies: 1319 1319
-- Name: horario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT horario_pkey PRIMARY KEY (idhorario);


--
-- TOC entry 1717 (class 2606 OID 19913)
-- Dependencies: 1329 1329
-- Name: notas_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY notas
    ADD CONSTRAINT notas_pkey PRIMARY KEY (idnotas);


--
-- TOC entry 1719 (class 2606 OID 19981)
-- Dependencies: 1330 1330
-- Name: objeto_nom_objeto_key; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY objeto
    ADD CONSTRAINT objeto_nom_objeto_key UNIQUE (nomobjeto);


--
-- TOC entry 1721 (class 2606 OID 19979)
-- Dependencies: 1330 1330
-- Name: objeto_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY objeto
    ADD CONSTRAINT objeto_pkey PRIMARY KEY (idobjeto);


--
-- TOC entry 1706 (class 2606 OID 19829)
-- Dependencies: 1320 1320
-- Name: paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (idpaciente);


--
-- TOC entry 1723 (class 2606 OID 19991)
-- Dependencies: 1332 1332
-- Name: permiso_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY permiso
    ADD CONSTRAINT permiso_pkey PRIMARY KEY (idpermiso);


--
-- TOC entry 1714 (class 2606 OID 19892)
-- Dependencies: 1327 1327
-- Name: reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (idreserva);


--
-- TOC entry 1698 (class 2606 OID 19790)
-- Dependencies: 1315 1315
-- Name: secretario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY secretario
    ADD CONSTRAINT secretario_pkey PRIMARY KEY (idsecretario);


--
-- TOC entry 1689 (class 2606 OID 19766)
-- Dependencies: 1310 1310
-- Name: tipo_sangre_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY tiposangre
    ADD CONSTRAINT tipo_sangre_pkey PRIMARY KEY (idsangre);


--
-- TOC entry 1710 (class 2606 OID 19860)
-- Dependencies: 1323 1323
-- Name: turno_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY turno
    ADD CONSTRAINT turno_pkey PRIMARY KEY (idturno);


--
-- TOC entry 1695 (class 2606 OID 19785)
-- Dependencies: 1314 1314
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- TOC entry 1691 (class 1259 OID 19778)
-- Dependencies: 1312
-- Name: especialidad_nom_especialidad_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX especialidad_nom_especialidad_unique ON especialidad USING btree (nomespecialidad);


--
-- TOC entry 1715 (class 1259 OID 19903)
-- Dependencies: 1327 1327
-- Name: reserva_turno_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX reserva_turno_unique ON reserva USING btree (fechareservada, idturno);


--
-- TOC entry 1690 (class 1259 OID 19767)
-- Dependencies: 1310
-- Name: tipo_sangre_tipo_sangre_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX tipo_sangre_tipo_sangre_unique ON tiposangre USING btree (tiposangre);


--
-- TOC entry 1696 (class 1259 OID 19786)
-- Dependencies: 1314
-- Name: usuario_userid_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX usuario_userid_unique ON usuario USING btree (userid);


--
-- TOC entry 1740 (class 2620 OID 19952)
-- Dependencies: 24 1327
-- Name: dias_coincidentes; Type: TRIGGER; Schema: public; Owner: webdr
--

CREATE TRIGGER dias_coincidentes
    BEFORE INSERT OR UPDATE ON reserva
    FOR EACH ROW
    EXECUTE PROCEDURE dias_coincidentes();


--
-- TOC entry 1790 (class 0 OID 0)
-- Dependencies: 1740
-- Name: TRIGGER dias_coincidentes ON reserva; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TRIGGER dias_coincidentes ON reserva IS 'Verifica que el dia de la fecha ingresada para la fecha de reserva, coincida con el dia del turno relacionado.';


--
-- TOC entry 1739 (class 2620 OID 19949)
-- Dependencies: 1323 23
-- Name: hora_en_rango; Type: TRIGGER; Schema: public; Owner: webdr
--

CREATE TRIGGER hora_en_rango
    BEFORE INSERT OR UPDATE ON turno
    FOR EACH ROW
    EXECUTE PROCEDURE hora_en_rango();


--
-- TOC entry 1791 (class 0 OID 0)
-- Dependencies: 1739
-- Name: TRIGGER hora_en_rango ON turno; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TRIGGER hora_en_rango ON turno IS 'Verifica que la hora de un turno esté dentro del rango de horas del horario relacionado.';


--
-- TOC entry 1738 (class 2620 OID 19957)
-- Dependencies: 26 1319
-- Name: horarios_no_solapados; Type: TRIGGER; Schema: public; Owner: webdr
--

CREATE TRIGGER horarios_no_solapados
    BEFORE INSERT OR UPDATE ON horario
    FOR EACH ROW
    EXECUTE PROCEDURE horarios_no_solapados();


--
-- TOC entry 1792 (class 0 OID 0)
-- Dependencies: 1738
-- Name: TRIGGER horarios_no_solapados ON horario; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TRIGGER horarios_no_solapados ON horario IS 'Verifica que dos horarios para un mismo doctor no estén solapados.';


--
-- TOC entry 1726 (class 2606 OID 19809)
-- Dependencies: 1317 1694 1314
-- Name: administrador_cod_administrador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_idadministrador_fkey FOREIGN KEY (idadministrador) REFERENCES usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1734 (class 2606 OID 19878)
-- Dependencies: 1316 1699 1325
-- Name: consulta_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_iddoctor_fkey FOREIGN KEY (iddoctor) REFERENCES doctor(iddoctor);


--
-- TOC entry 1733 (class 2606 OID 19873)
-- Dependencies: 1320 1325 1705
-- Name: consulta_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_idpaciente_fkey FOREIGN KEY (idpaciente) REFERENCES paciente(idpaciente);


--
-- TOC entry 1725 (class 2606 OID 19800)
-- Dependencies: 1694 1316 1314
-- Name: doctor_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_iddoctor_fkey FOREIGN KEY (iddoctor) REFERENCES usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1730 (class 2606 OID 19844)
-- Dependencies: 1699 1321 1316
-- Name: doctor_especialidad_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_iddoctor_fkey FOREIGN KEY (iddoctor) REFERENCES doctor(iddoctor) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1731 (class 2606 OID 19849)
-- Dependencies: 1321 1312 1692
-- Name: doctor_especialidad_cod_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_idespecialidad_fkey FOREIGN KEY (idespecialidad) REFERENCES especialidad(idespecialidad) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1727 (class 2606 OID 19821)
-- Dependencies: 1319 1316 1699
-- Name: horario_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT horario_iddoctor_fkey FOREIGN KEY (iddoctor) REFERENCES doctor(iddoctor) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1737 (class 2606 OID 19914)
-- Dependencies: 1329 1711 1325
-- Name: notas_cod_consulta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY notas
    ADD CONSTRAINT notas_idconsulta_fkey FOREIGN KEY (idconsulta) REFERENCES consulta(idconsulta) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1729 (class 2606 OID 19835)
-- Dependencies: 1694 1320 1314
-- Name: paciente_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_idpaciente_fkey FOREIGN KEY (idpaciente) REFERENCES usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1728 (class 2606 OID 19830)
-- Dependencies: 1310 1320 1688
-- Name: paciente_tipo_sangre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_tiposangre_fkey FOREIGN KEY (tiposangre) REFERENCES tiposangre(idsangre);


--
-- TOC entry 1736 (class 2606 OID 19898)
-- Dependencies: 1705 1327 1320
-- Name: reserva_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idpaciente_fkey FOREIGN KEY (idpaciente) REFERENCES paciente(idpaciente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1735 (class 2606 OID 19893)
-- Dependencies: 1323 1709 1327
-- Name: reserva_cod_turno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_idturno_fkey FOREIGN KEY (idturno) REFERENCES turno(idturno) ON UPDATE SET NULL ON DELETE SET NULL;


--
-- TOC entry 1724 (class 2606 OID 19791)
-- Dependencies: 1314 1315 1694
-- Name: secretario_cod_secretario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY secretario
    ADD CONSTRAINT secretario_idsecretario_fkey FOREIGN KEY (idsecretario) REFERENCES usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1732 (class 2606 OID 19861)
-- Dependencies: 1703 1323 1319
-- Name: turno_cod_horario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY turno
    ADD CONSTRAINT turno_idhorario_fkey FOREIGN KEY (idhorario) REFERENCES horario(idhorario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1760 (class 0 OID 0)
-- Dependencies: 4
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2007-10-08 18:59:20

--
-- PostgreSQL database dump complete
--


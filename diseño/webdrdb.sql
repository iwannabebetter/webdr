--
-- PostgreSQL database dump
--

-- Started on 2007-10-06 20:23:31

SET client_encoding = 'LATIN1';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1740 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 297 (class 2612 OID 16386)
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
-- Dependencies: 19 18 4
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
-- TOC entry 1742 (class 0 OID 0)
-- Dependencies: 261
-- Name: TYPE chkpass; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TYPE chkpass IS 'password type with checks';


--
-- TOC entry 26 (class 1255 OID 19956)
-- Dependencies: 4 297
-- Name: coincide_horario(time without time zone, time without time zone, time without time zone, time without time zone); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) RETURNS boolean
    AS 'BEGIN
END;'
    LANGUAGE plpgsql;


ALTER FUNCTION public.coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) OWNER TO webdr;

--
-- TOC entry 1743 (class 0 OID 0)
-- Dependencies: 26
-- Name: FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION coincide_horario(ini1 time without time zone, fin1 time without time zone, ini2 time without time zone, fin2 time without time zone) IS 'Verifica si dos horarios se solapan.';


--
-- TOC entry 24 (class 1255 OID 19950)
-- Dependencies: 297 4
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
-- TOC entry 1744 (class 0 OID 0)
-- Dependencies: 24
-- Name: FUNCTION dias_coincidentes(); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION dias_coincidentes() IS 'Verifica que el dia de la fecha ingresada para la fecha de reserva, coincida con el dia del turno relacionado.';


--
-- TOC entry 21 (class 1255 OID 19611)
-- Dependencies: 261 4
-- Name: eq(chkpass, text); Type: FUNCTION; Schema: public; Owner: webdr
--

CREATE FUNCTION eq(chkpass, text) RETURNS boolean
    AS '$libdir/chkpass', 'chkpass_eq'
    LANGUAGE c STRICT;


ALTER FUNCTION public.eq(chkpass, text) OWNER TO webdr;

--
-- TOC entry 23 (class 1255 OID 19948)
-- Dependencies: 4 297
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
-- TOC entry 1745 (class 0 OID 0)
-- Dependencies: 23
-- Name: FUNCTION hora_en_rango(); Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON FUNCTION hora_en_rango() IS 'Verifica que la hora de un turno esté dentro del rango de horas del horario relacionado.';


--
-- TOC entry 27 (class 1255 OID 19955)
-- Dependencies: 297 4
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
-- TOC entry 1746 (class 0 OID 0)
-- Dependencies: 27
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
-- TOC entry 965 (class 2617 OID 19615)
-- Dependencies: 22 261 4
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
-- TOC entry 966 (class 2617 OID 19614)
-- Dependencies: 261 4 21
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
-- TOC entry 1313 (class 1259 OID 19805)
-- Dependencies: 4
-- Name: administrador; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE administrador (
    cod_administrador bigint NOT NULL
);


ALTER TABLE public.administrador OWNER TO webdr;

--
-- TOC entry 1321 (class 1259 OID 19868)
-- Dependencies: 1668 1669 4
-- Name: consulta; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE consulta (
    cod_consulta bigint NOT NULL,
    fecha date NOT NULL,
    hora_inicio time without time zone,
    hora_fin time without time zone,
    cod_paciente bigint NOT NULL,
    cod_doctor bigint NOT NULL,
    CONSTRAINT consulta_check CHECK ((hora_inicio < hora_fin)),
    CONSTRAINT consulta_fecha_check CHECK ((fecha <= ('now'::text)::date))
);


ALTER TABLE public.consulta OWNER TO webdr;

--
-- TOC entry 1320 (class 1259 OID 19866)
-- Dependencies: 4 1321
-- Name: consulta_cod_consulta_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE consulta_cod_consulta_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.consulta_cod_consulta_seq OWNER TO webdr;

--
-- TOC entry 1747 (class 0 OID 0)
-- Dependencies: 1320
-- Name: consulta_cod_consulta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE consulta_cod_consulta_seq OWNED BY consulta.cod_consulta;


--
-- TOC entry 1748 (class 0 OID 0)
-- Dependencies: 1320
-- Name: consulta_cod_consulta_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('consulta_cod_consulta_seq', 1, false);


--
-- TOC entry 1312 (class 1259 OID 19796)
-- Dependencies: 4
-- Name: doctor; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE doctor (
    cod_doctor bigint NOT NULL
);


ALTER TABLE public.doctor OWNER TO webdr;

--
-- TOC entry 1317 (class 1259 OID 19840)
-- Dependencies: 4
-- Name: doctor_especialidad; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE doctor_especialidad (
    cod_doctor bigint NOT NULL,
    cod_especialidad bigint NOT NULL
);


ALTER TABLE public.doctor_especialidad OWNER TO webdr;

--
-- TOC entry 1308 (class 1259 OID 19770)
-- Dependencies: 4
-- Name: especialidad; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE especialidad (
    cod_especialidad bigint NOT NULL,
    nom_especialidad character varying(50) NOT NULL,
    descripcion text
);


ALTER TABLE public.especialidad OWNER TO webdr;

--
-- TOC entry 1307 (class 1259 OID 19768)
-- Dependencies: 4 1308
-- Name: especialidad_cod_especialidad_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE especialidad_cod_especialidad_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.especialidad_cod_especialidad_seq OWNER TO webdr;

--
-- TOC entry 1749 (class 0 OID 0)
-- Dependencies: 1307
-- Name: especialidad_cod_especialidad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE especialidad_cod_especialidad_seq OWNED BY especialidad.cod_especialidad;


--
-- TOC entry 1750 (class 0 OID 0)
-- Dependencies: 1307
-- Name: especialidad_cod_especialidad_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('especialidad_cod_especialidad_seq', 1, false);


--
-- TOC entry 1315 (class 1259 OID 19816)
-- Dependencies: 1663 1664 4
-- Name: horario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE horario (
    cod_horario bigint NOT NULL,
    hora_inicio time without time zone NOT NULL,
    hora_fin time without time zone NOT NULL,
    cod_doctor bigint NOT NULL,
    dia integer NOT NULL,
    CONSTRAINT horario_check CHECK ((hora_inicio < hora_fin)),
    CONSTRAINT horario_dia_check CHECK (((dia >= 0) AND (dia <= 6)))
);


ALTER TABLE public.horario OWNER TO webdr;

--
-- TOC entry 1751 (class 0 OID 0)
-- Dependencies: 1315
-- Name: CONSTRAINT horario_dia_check ON horario; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON CONSTRAINT horario_dia_check ON horario IS 'El 0 corresponde a Domingo.';


--
-- TOC entry 1314 (class 1259 OID 19814)
-- Dependencies: 1315 4
-- Name: horario_cod_horario_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE horario_cod_horario_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.horario_cod_horario_seq OWNER TO webdr;

--
-- TOC entry 1752 (class 0 OID 0)
-- Dependencies: 1314
-- Name: horario_cod_horario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE horario_cod_horario_seq OWNED BY horario.cod_horario;


--
-- TOC entry 1753 (class 0 OID 0)
-- Dependencies: 1314
-- Name: horario_cod_horario_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('horario_cod_horario_seq', 1, false);


--
-- TOC entry 1325 (class 1259 OID 19906)
-- Dependencies: 1674 1675 1676 1677 4
-- Name: notas; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE notas (
    cod_notas bigint NOT NULL,
    cod_consulta bigint NOT NULL,
    sintomas text NOT NULL,
    diagnostico text NOT NULL,
    indicaciones text NOT NULL,
    recetario text,
    peso real,
    altura integer,
    edad integer,
    unidad_edad character(1),
    CONSTRAINT notas_altura_check CHECK ((altura > 0)),
    CONSTRAINT notas_edad_check CHECK ((edad > 0)),
    CONSTRAINT notas_peso_check CHECK ((peso > (0)::double precision)),
    CONSTRAINT notas_unidad_edad_check CHECK ((unidad_edad = ANY (ARRAY['A'::bpchar, 'M'::bpchar])))
);


ALTER TABLE public.notas OWNER TO webdr;

--
-- TOC entry 1754 (class 0 OID 0)
-- Dependencies: 1325
-- Name: COLUMN notas.altura; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.altura IS 'En centímetros';


--
-- TOC entry 1755 (class 0 OID 0)
-- Dependencies: 1325
-- Name: COLUMN notas.edad; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.edad IS 'En meses o años';


--
-- TOC entry 1756 (class 0 OID 0)
-- Dependencies: 1325
-- Name: COLUMN notas.unidad_edad; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON COLUMN notas.unidad_edad IS 'En meses o años';


--
-- TOC entry 1324 (class 1259 OID 19904)
-- Dependencies: 1325 4
-- Name: notas_cod_notas_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE notas_cod_notas_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.notas_cod_notas_seq OWNER TO webdr;

--
-- TOC entry 1757 (class 0 OID 0)
-- Dependencies: 1324
-- Name: notas_cod_notas_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE notas_cod_notas_seq OWNED BY notas.cod_notas;


--
-- TOC entry 1758 (class 0 OID 0)
-- Dependencies: 1324
-- Name: notas_cod_notas_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('notas_cod_notas_seq', 1, false);


--
-- TOC entry 1316 (class 1259 OID 19826)
-- Dependencies: 1665 4
-- Name: paciente; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE paciente (
    cod_paciente bigint NOT NULL,
    tipo_sangre bigint NOT NULL,
    fecha_ingreso date NOT NULL,
    CONSTRAINT paciente_fecha_ingreso_check CHECK ((fecha_ingreso <= ('now'::text)::date))
);


ALTER TABLE public.paciente OWNER TO webdr;

--
-- TOC entry 1323 (class 1259 OID 19885)
-- Dependencies: 1671 1672 4
-- Name: reserva; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE reserva (
    cod_reserva bigint NOT NULL,
    fecha_reserva timestamp without time zone NOT NULL,
    fecha_reservada date NOT NULL,
    cancelado boolean DEFAULT false,
    observ_cancelado text,
    cod_paciente bigint NOT NULL,
    cod_turno bigint NOT NULL,
    CONSTRAINT reserva_fecha_reserva_check CHECK ((fecha_reserva <= now()))
);


ALTER TABLE public.reserva OWNER TO webdr;

--
-- TOC entry 1322 (class 1259 OID 19883)
-- Dependencies: 4 1323
-- Name: reserva_cod_reserva_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE reserva_cod_reserva_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.reserva_cod_reserva_seq OWNER TO webdr;

--
-- TOC entry 1759 (class 0 OID 0)
-- Dependencies: 1322
-- Name: reserva_cod_reserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE reserva_cod_reserva_seq OWNED BY reserva.cod_reserva;


--
-- TOC entry 1760 (class 0 OID 0)
-- Dependencies: 1322
-- Name: reserva_cod_reserva_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('reserva_cod_reserva_seq', 1, false);


--
-- TOC entry 1311 (class 1259 OID 19787)
-- Dependencies: 4
-- Name: secretario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE secretario (
    cod_secretario bigint NOT NULL
);


ALTER TABLE public.secretario OWNER TO webdr;

--
-- TOC entry 1306 (class 1259 OID 19762)
-- Dependencies: 4
-- Name: tipo_sangre; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE tipo_sangre (
    cod_sangre bigint NOT NULL,
    tipo_sangre character(5) NOT NULL
);


ALTER TABLE public.tipo_sangre OWNER TO webdr;

--
-- TOC entry 1305 (class 1259 OID 19760)
-- Dependencies: 1306 4
-- Name: tipo_sangre_cod_sangre_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE tipo_sangre_cod_sangre_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipo_sangre_cod_sangre_seq OWNER TO webdr;

--
-- TOC entry 1761 (class 0 OID 0)
-- Dependencies: 1305
-- Name: tipo_sangre_cod_sangre_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE tipo_sangre_cod_sangre_seq OWNED BY tipo_sangre.cod_sangre;


--
-- TOC entry 1762 (class 0 OID 0)
-- Dependencies: 1305
-- Name: tipo_sangre_cod_sangre_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('tipo_sangre_cod_sangre_seq', 8, true);


--
-- TOC entry 1319 (class 1259 OID 19856)
-- Dependencies: 4
-- Name: turno; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE turno (
    cod_turno bigint NOT NULL,
    cod_horario bigint NOT NULL,
    hora time without time zone
);


ALTER TABLE public.turno OWNER TO webdr;

--
-- TOC entry 1318 (class 1259 OID 19854)
-- Dependencies: 4 1319
-- Name: turno_cod_turno_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE turno_cod_turno_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.turno_cod_turno_seq OWNER TO webdr;

--
-- TOC entry 1763 (class 0 OID 0)
-- Dependencies: 1318
-- Name: turno_cod_turno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE turno_cod_turno_seq OWNED BY turno.cod_turno;


--
-- TOC entry 1764 (class 0 OID 0)
-- Dependencies: 1318
-- Name: turno_cod_turno_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('turno_cod_turno_seq', 1, false);


--
-- TOC entry 1310 (class 1259 OID 19781)
-- Dependencies: 1659 1660 1661 4 261
-- Name: usuario; Type: TABLE; Schema: public; Owner: webdr; Tablespace: 
--

CREATE TABLE usuario (
    cod_usuario bigint NOT NULL,
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
-- TOC entry 1309 (class 1259 OID 19779)
-- Dependencies: 1310 4
-- Name: usuario_cod_usuario_seq; Type: SEQUENCE; Schema: public; Owner: webdr
--

CREATE SEQUENCE usuario_cod_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_cod_usuario_seq OWNER TO webdr;

--
-- TOC entry 1765 (class 0 OID 0)
-- Dependencies: 1309
-- Name: usuario_cod_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: webdr
--

ALTER SEQUENCE usuario_cod_usuario_seq OWNED BY usuario.cod_usuario;


--
-- TOC entry 1766 (class 0 OID 0)
-- Dependencies: 1309
-- Name: usuario_cod_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: webdr
--

SELECT pg_catalog.setval('usuario_cod_usuario_seq', 1, false);


--
-- TOC entry 1667 (class 2604 OID 19870)
-- Dependencies: 1320 1321 1321
-- Name: cod_consulta; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE consulta ALTER COLUMN cod_consulta SET DEFAULT nextval('consulta_cod_consulta_seq'::regclass);


--
-- TOC entry 1657 (class 2604 OID 19772)
-- Dependencies: 1308 1307 1308
-- Name: cod_especialidad; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE especialidad ALTER COLUMN cod_especialidad SET DEFAULT nextval('especialidad_cod_especialidad_seq'::regclass);


--
-- TOC entry 1662 (class 2604 OID 19818)
-- Dependencies: 1314 1315 1315
-- Name: cod_horario; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE horario ALTER COLUMN cod_horario SET DEFAULT nextval('horario_cod_horario_seq'::regclass);


--
-- TOC entry 1673 (class 2604 OID 19908)
-- Dependencies: 1324 1325 1325
-- Name: cod_notas; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE notas ALTER COLUMN cod_notas SET DEFAULT nextval('notas_cod_notas_seq'::regclass);


--
-- TOC entry 1670 (class 2604 OID 19887)
-- Dependencies: 1323 1322 1323
-- Name: cod_reserva; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE reserva ALTER COLUMN cod_reserva SET DEFAULT nextval('reserva_cod_reserva_seq'::regclass);


--
-- TOC entry 1656 (class 2604 OID 19764)
-- Dependencies: 1305 1306 1306
-- Name: cod_sangre; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE tipo_sangre ALTER COLUMN cod_sangre SET DEFAULT nextval('tipo_sangre_cod_sangre_seq'::regclass);


--
-- TOC entry 1666 (class 2604 OID 19858)
-- Dependencies: 1319 1318 1319
-- Name: cod_turno; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE turno ALTER COLUMN cod_turno SET DEFAULT nextval('turno_cod_turno_seq'::regclass);


--
-- TOC entry 1658 (class 2604 OID 19783)
-- Dependencies: 1309 1310 1310
-- Name: cod_usuario; Type: DEFAULT; Schema: public; Owner: webdr
--

ALTER TABLE usuario ALTER COLUMN cod_usuario SET DEFAULT nextval('usuario_cod_usuario_seq'::regclass);


--
-- TOC entry 1729 (class 0 OID 19805)
-- Dependencies: 1313
-- Data for Name: administrador; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1734 (class 0 OID 19868)
-- Dependencies: 1321
-- Data for Name: consulta; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1728 (class 0 OID 19796)
-- Dependencies: 1312
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1732 (class 0 OID 19840)
-- Dependencies: 1317
-- Data for Name: doctor_especialidad; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1725 (class 0 OID 19770)
-- Dependencies: 1308
-- Data for Name: especialidad; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1730 (class 0 OID 19816)
-- Dependencies: 1315
-- Data for Name: horario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1736 (class 0 OID 19906)
-- Dependencies: 1325
-- Data for Name: notas; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1731 (class 0 OID 19826)
-- Dependencies: 1316
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1735 (class 0 OID 19885)
-- Dependencies: 1323
-- Data for Name: reserva; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1727 (class 0 OID 19787)
-- Dependencies: 1311
-- Data for Name: secretario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1724 (class 0 OID 19762)
-- Dependencies: 1306
-- Data for Name: tipo_sangre; Type: TABLE DATA; Schema: public; Owner: webdr
--

INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (1, 'AB+  ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (2, 'AB-  ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (3, 'A+   ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (4, 'A-   ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (5, 'B+   ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (6, 'B-   ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (7, 'O+   ');
INSERT INTO tipo_sangre (cod_sangre, tipo_sangre) VALUES (8, 'O-   ');


--
-- TOC entry 1733 (class 0 OID 19856)
-- Dependencies: 1319
-- Data for Name: turno; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1726 (class 0 OID 19781)
-- Dependencies: 1310
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: webdr
--



--
-- TOC entry 1692 (class 2606 OID 19808)
-- Dependencies: 1313 1313
-- Name: administrador_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (cod_administrador);


--
-- TOC entry 1702 (class 2606 OID 19872)
-- Dependencies: 1321 1321
-- Name: consulta_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_pkey PRIMARY KEY (cod_consulta);


--
-- TOC entry 1698 (class 2606 OID 19843)
-- Dependencies: 1317 1317 1317
-- Name: doctor_especialidad_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_pkey PRIMARY KEY (cod_doctor, cod_especialidad);


--
-- TOC entry 1690 (class 2606 OID 19799)
-- Dependencies: 1312 1312
-- Name: doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (cod_doctor);


--
-- TOC entry 1683 (class 2606 OID 19777)
-- Dependencies: 1308 1308
-- Name: especialidad_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY especialidad
    ADD CONSTRAINT especialidad_pkey PRIMARY KEY (cod_especialidad);


--
-- TOC entry 1694 (class 2606 OID 19820)
-- Dependencies: 1315 1315
-- Name: horario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT horario_pkey PRIMARY KEY (cod_horario);


--
-- TOC entry 1707 (class 2606 OID 19913)
-- Dependencies: 1325 1325
-- Name: notas_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY notas
    ADD CONSTRAINT notas_pkey PRIMARY KEY (cod_notas);


--
-- TOC entry 1696 (class 2606 OID 19829)
-- Dependencies: 1316 1316
-- Name: paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (cod_paciente);


--
-- TOC entry 1704 (class 2606 OID 19892)
-- Dependencies: 1323 1323
-- Name: reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (cod_reserva);


--
-- TOC entry 1688 (class 2606 OID 19790)
-- Dependencies: 1311 1311
-- Name: secretario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY secretario
    ADD CONSTRAINT secretario_pkey PRIMARY KEY (cod_secretario);


--
-- TOC entry 1679 (class 2606 OID 19766)
-- Dependencies: 1306 1306
-- Name: tipo_sangre_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY tipo_sangre
    ADD CONSTRAINT tipo_sangre_pkey PRIMARY KEY (cod_sangre);


--
-- TOC entry 1700 (class 2606 OID 19860)
-- Dependencies: 1319 1319
-- Name: turno_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY turno
    ADD CONSTRAINT turno_pkey PRIMARY KEY (cod_turno);


--
-- TOC entry 1685 (class 2606 OID 19785)
-- Dependencies: 1310 1310
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: webdr; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (cod_usuario);


--
-- TOC entry 1681 (class 1259 OID 19778)
-- Dependencies: 1308
-- Name: especialidad_nom_especialidad_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX especialidad_nom_especialidad_unique ON especialidad USING btree (nom_especialidad);


--
-- TOC entry 1705 (class 1259 OID 19903)
-- Dependencies: 1323 1323
-- Name: reserva_turno_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX reserva_turno_unique ON reserva USING btree (fecha_reservada, cod_turno);


--
-- TOC entry 1680 (class 1259 OID 19767)
-- Dependencies: 1306
-- Name: tipo_sangre_tipo_sangre_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX tipo_sangre_tipo_sangre_unique ON tipo_sangre USING btree (tipo_sangre);


--
-- TOC entry 1686 (class 1259 OID 19786)
-- Dependencies: 1310
-- Name: usuario_userid_unique; Type: INDEX; Schema: public; Owner: webdr; Tablespace: 
--

CREATE UNIQUE INDEX usuario_userid_unique ON usuario USING btree (userid);


--
-- TOC entry 1723 (class 2620 OID 19952)
-- Dependencies: 1323 24
-- Name: dias_coincidentes; Type: TRIGGER; Schema: public; Owner: webdr
--

CREATE TRIGGER dias_coincidentes
    BEFORE INSERT OR UPDATE ON reserva
    FOR EACH ROW
    EXECUTE PROCEDURE dias_coincidentes();


--
-- TOC entry 1767 (class 0 OID 0)
-- Dependencies: 1723
-- Name: TRIGGER dias_coincidentes ON reserva; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TRIGGER dias_coincidentes ON reserva IS 'Verifica que el dia de la fecha ingresada para la fecha de reserva, coincida con el dia del turno relacionado.';


--
-- TOC entry 1722 (class 2620 OID 19949)
-- Dependencies: 1319 23
-- Name: hora_en_rango; Type: TRIGGER; Schema: public; Owner: webdr
--

CREATE TRIGGER hora_en_rango
    BEFORE INSERT OR UPDATE ON turno
    FOR EACH ROW
    EXECUTE PROCEDURE hora_en_rango();


--
-- TOC entry 1768 (class 0 OID 0)
-- Dependencies: 1722
-- Name: TRIGGER hora_en_rango ON turno; Type: COMMENT; Schema: public; Owner: webdr
--

COMMENT ON TRIGGER hora_en_rango ON turno IS 'Verifica que la hora de un turno esté dentro del rango de horas del horario relacionado.';


--
-- TOC entry 1710 (class 2606 OID 19809)
-- Dependencies: 1684 1310 1313
-- Name: administrador_cod_administrador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_cod_administrador_fkey FOREIGN KEY (cod_administrador) REFERENCES usuario(cod_usuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1718 (class 2606 OID 19878)
-- Dependencies: 1312 1689 1321
-- Name: consulta_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_cod_doctor_fkey FOREIGN KEY (cod_doctor) REFERENCES doctor(cod_doctor);


--
-- TOC entry 1717 (class 2606 OID 19873)
-- Dependencies: 1695 1321 1316
-- Name: consulta_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY consulta
    ADD CONSTRAINT consulta_cod_paciente_fkey FOREIGN KEY (cod_paciente) REFERENCES paciente(cod_paciente);


--
-- TOC entry 1709 (class 2606 OID 19800)
-- Dependencies: 1310 1684 1312
-- Name: doctor_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_cod_doctor_fkey FOREIGN KEY (cod_doctor) REFERENCES usuario(cod_usuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1714 (class 2606 OID 19844)
-- Dependencies: 1317 1689 1312
-- Name: doctor_especialidad_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_cod_doctor_fkey FOREIGN KEY (cod_doctor) REFERENCES doctor(cod_doctor) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1715 (class 2606 OID 19849)
-- Dependencies: 1317 1308 1682
-- Name: doctor_especialidad_cod_especialidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY doctor_especialidad
    ADD CONSTRAINT doctor_especialidad_cod_especialidad_fkey FOREIGN KEY (cod_especialidad) REFERENCES especialidad(cod_especialidad) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1711 (class 2606 OID 19821)
-- Dependencies: 1315 1312 1689
-- Name: horario_cod_doctor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT horario_cod_doctor_fkey FOREIGN KEY (cod_doctor) REFERENCES doctor(cod_doctor) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1721 (class 2606 OID 19914)
-- Dependencies: 1325 1701 1321
-- Name: notas_cod_consulta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY notas
    ADD CONSTRAINT notas_cod_consulta_fkey FOREIGN KEY (cod_consulta) REFERENCES consulta(cod_consulta) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1713 (class 2606 OID 19835)
-- Dependencies: 1684 1316 1310
-- Name: paciente_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_cod_paciente_fkey FOREIGN KEY (cod_paciente) REFERENCES usuario(cod_usuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1712 (class 2606 OID 19830)
-- Dependencies: 1316 1306 1678
-- Name: paciente_tipo_sangre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_tipo_sangre_fkey FOREIGN KEY (tipo_sangre) REFERENCES tipo_sangre(cod_sangre);


--
-- TOC entry 1720 (class 2606 OID 19898)
-- Dependencies: 1316 1695 1323
-- Name: reserva_cod_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_cod_paciente_fkey FOREIGN KEY (cod_paciente) REFERENCES paciente(cod_paciente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1719 (class 2606 OID 19893)
-- Dependencies: 1323 1699 1319
-- Name: reserva_cod_turno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY reserva
    ADD CONSTRAINT reserva_cod_turno_fkey FOREIGN KEY (cod_turno) REFERENCES turno(cod_turno) ON UPDATE SET NULL ON DELETE SET NULL;


--
-- TOC entry 1708 (class 2606 OID 19791)
-- Dependencies: 1311 1684 1310
-- Name: secretario_cod_secretario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY secretario
    ADD CONSTRAINT secretario_cod_secretario_fkey FOREIGN KEY (cod_secretario) REFERENCES usuario(cod_usuario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1716 (class 2606 OID 19861)
-- Dependencies: 1693 1319 1315
-- Name: turno_cod_horario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: webdr
--

ALTER TABLE ONLY turno
    ADD CONSTRAINT turno_cod_horario_fkey FOREIGN KEY (cod_horario) REFERENCES horario(cod_horario) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1741 (class 0 OID 0)
-- Dependencies: 4
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2007-10-06 20:23:32

--
-- PostgreSQL database dump complete
--


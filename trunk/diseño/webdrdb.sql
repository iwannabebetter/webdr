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


INSERT INTO tiposangre (idsangre, tiposangre) VALUES (1, 'AB+  ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (2, 'AB-  ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (3, 'A+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (4, 'A-   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (5, 'B+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (6, 'B-   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (7, 'O+   ');
INSERT INTO tiposangre (idsangre, tiposangre) VALUES (8, 'O-   ');


CREATE TRIGGER dias_coincidentes
    BEFORE INSERT OR UPDATE ON reserva
    FOR EACH ROW
    EXECUTE PROCEDURE dias_coincidentes();


CREATE TRIGGER hora_en_rango
    BEFORE INSERT OR UPDATE ON turno
    FOR EACH ROW
    EXECUTE PROCEDURE hora_en_rango();


CREATE TRIGGER horarios_no_solapados
    BEFORE INSERT OR UPDATE ON horario
    FOR EACH ROW
    EXECUTE PROCEDURE horarios_no_solapados();

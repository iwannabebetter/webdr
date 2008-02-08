package edu.fpuna.webapp.taglib;

import java.io.IOException;

import java.text.Collator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import edu.fpuna.model.LabelValue;

import org.displaytag.tags.el.ExpressionEvaluator;

/**
 * Tag for creating multiple &lt;select&gt; options for displaying a list of
 * country names.
 *
 */
public class DiaSemanaTag extends TagSupport {
    private static final long serialVersionUID = 3905528206810167095L;
    private String name;
    private String prompt;
    private String scope;
    private String selected;
    private String[] diasSemanas ={"Lunes","Martes","Miercoles","Jueves",
                                    "Viernes","Sabado","Domingo"};

    public void setName(String name) {
        this.name = name;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setDefault(String selected) {
        this.selected = selected;
    }

    public void setToScope(String scope) {
        this.scope = scope;
    }

    /**
     * Process the start of this tag.
     *
     * @return int status
     * @exception JspException if a JSP exception has occurred
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (selected != null) {
            selected = eval.evalString("default", selected);
        }

        //Locale userLocale = pageContext.getRequest().getLocale();
        List dias = this.buildDiasList();
        

        if (scope != null) {
            if (scope.equals("page")) {
                pageContext.setAttribute(name, dias);
            } else if (scope.equals("request")) {
                pageContext.getRequest().setAttribute(name, dias);
            } else if (scope.equals("session")) {
                pageContext.getSession().setAttribute(name, dias);
            } else if (scope.equals("application")) {
                pageContext.getServletContext().setAttribute(name, dias);
            } else {
                throw new JspException("Attribute 'scope' must be: page, request, session or application");
            }
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("<select name=\"").append(name).append("\" id=\"").append(name).append("\" class=\"select\">\n");

            if (prompt != null) {
                sb.append("    <option value=\"\" selected=\"selected\">");
                sb.append(eval.evalString("prompt", prompt)).append("</option>\n");
            }

            for (Object dia1 : dias) {
                LabelValue dia = (LabelValue) dia1;
                sb.append("    <option value=\"").append(dia.getValue()).append("\"");

                if ((selected != null) && selected.trim().equalsIgnoreCase(dia.getValue().trim())) {
                    sb.append(" selected=\"selected\"");
                }

                sb.append(">").append(dia.getLabel()).append("</option>\n");
            }

            sb.append("</select>");

            try {
                pageContext.getOut().write(sb.toString());
            } catch (IOException io) {
                throw new JspException(io);
            }
        }

        return super.doStartTag();
    }

    /**
     * Release aquired resources to enable tag reusage.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
    }

    /**
     * Construye una lista con los dias de la semana
     * 
     * @param locale The Locale used to localize the country names.
     *
     * @return List of LabelValues for all available countries.
     */
    @SuppressWarnings("unchecked")
    protected List<LabelValue> buildDiasList() {
        List<LabelValue> diasSemana = new ArrayList<LabelValue>();
        for (String dia : this.diasSemanas) {
            diasSemana.add(new LabelValue(dia, dia));
        }
        return diasSemana;
    }

    /**
     * Class to compare LabelValues using their labels with
     * locale-sensitive behaviour.
     
    public class LabelValueComparator implements Comparator {
        private Comparator c;

        public LabelValueComparator(final Locale locale) {
            c = Collator.getInstance(locale);
        }

        @SuppressWarnings("unchecked")
        public final int compare(Object o1, Object o2) {
            LabelValue lhs = (LabelValue) o1;
            LabelValue rhs = (LabelValue) o2;

            return c.compare(lhs.getLabel(), rhs.getLabel());
        }
    }*/
}

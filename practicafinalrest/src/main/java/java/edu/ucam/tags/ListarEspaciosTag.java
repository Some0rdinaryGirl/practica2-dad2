package edu.ucam.tags;

import jakarta.servlet.jsp.tagext.TagSupport;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import edu.ucam.domain.Espacio;

public class ListarEspaciosTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private Set<Espacio> espacios;

    public void setEspacios(HashSet<Espacio> espacios) {
        this.espacios = espacios;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String contextPath = pageContext.getServletContext().getContextPath();

            if (espacios != null && !espacios.isEmpty()) {
                out.write("<ul>");
                for (Espacio espacio : espacios) {
                    out.write("<li>");
                    out.write(espacio.toString());

                    // Formulario para eliminar
                    out.write("<form action='" + contextPath + "/control' method='post' style='display:inline; margin-left:10px;'>");
                    out.write("<input type='hidden' name='accion' value='eliminarespacio'/>");
                    out.write("<input type='hidden' name='PARAM_NOMBRE' value='" + espacio.getNombre() + "'/>");
                    out.write("<input type='submit' value='Eliminar'/>");
                    out.write("</form>");

                    // Formulario para editar
                    out.write("<form action='" + contextPath + "/secured/admin/editEspacio.jsp' method='get' style='display:inline; margin-left:5px;'>");
                    out.write("<input type='hidden' name='nombre' value='" + espacio.getNombre() + "'/>");
                    out.write("<input type='submit' value='Editar'/>");
                    out.write("</form>");

                    out.write("</li>");
                }
                out.write("</ul>");
            } else {
                out.write("<p>No hay espacios disponibles.</p>");
            }

        } catch (IOException e) {
            throw new JspException("Error al generar la lista de espacios: " + e.getMessage(), e);
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}

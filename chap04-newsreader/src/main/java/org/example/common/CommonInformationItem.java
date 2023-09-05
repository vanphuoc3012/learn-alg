package org.example.common;

import java.io.StringWriter;
import java.util.Date;
import java.util.Objects;

public class CommonInformationItem {
    private String title;
    private String txtDate;
    private Date date;
    private String link;
    private StringBuffer description;
    private String id;
    private String source;

    public CommonInformationItem() {
        description = new StringBuffer();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toXML() {
        StringWriter writer = new StringWriter();
        writer.append("<item>\n");
        writer.append("<ide>\n");
        writer.append(id);
        writer.append("\n</id>\n");
        writer.append("\n<title>\n");
        writer.append(title);
        writer.append("\n</title>\n");
        writer.append("\n<date>\n");
        writer.append(txtDate);
        writer.append("\n</date>\n");
        writer.append("\n<link>\n");
        writer.append(link);
        writer.append("\n</link>\n");
        writer.append("\n<description>\n");
        writer.append(description);
        writer.append("\n</description>\n");
        writer.append("\n</item>\n");

        return writer.toString();
    }

    public String getFileName() {
        StringWriter writer = new StringWriter();
        writer.append(source);
        writer.append("_");
        writer.append(String.valueOf(description.hashCode()));
        writer.append(".xml");

        return writer.toString();
    }

    public void addDescripcion(String txt) {
        description.append(txt);
    }

    public StringBuffer getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonInformationItem that = (CommonInformationItem) o;
        return Objects.equals(title, that.title) && Objects.equals(txtDate,
                                                                   that.txtDate) && Objects.equals(
                date, that.date) && Objects.equals(link, that.link) && Objects.equals(description,
                                                                                      that.description) && Objects.equals(
                id, that.id) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, txtDate, date, link, description, id, source);
    }
}

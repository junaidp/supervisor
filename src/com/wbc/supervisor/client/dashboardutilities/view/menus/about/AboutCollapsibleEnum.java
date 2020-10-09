package com.wbc.supervisor.client.dashboardutilities.view.menus.about;
import com.google.gwt.core.client.GWT;

public enum AboutCollapsibleEnum
{

    GWT(0, "Google Web Toolkit",
            "<a href=\"http://www.gwtproject.org/GWTPolicy.html\" target=\"_blank\">GWT</a>" +
            " is a development toolkit for building and optimizing complex browser-based applications. Its goal is to enable " +
            "productive development of high-performance web applications without the developer having to be an expert in browser quirks, XMLHttpRequest and JavaScript."),
    APACHECOMMON(1, "Apache commons",
            "The Service contains Apache-Commons and is used under " +
                    "Apache License, version 2.0.  A copy of the license is at <a href=\"https://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\">Apache 2.0 License</a><br>" +
                    "  Commons Proper is dedicated to one principal goal: creating and maintaining reusable Java components. " +
                    "The Commons Proper is a place for collaboration and sharing, where developers from throughout the Apache community " +
                    "can work together on projects to be shared by the Apache projects and Apache user "),
    LOG4J(2, "Log 4J",
            "The Service contains Apache-Logging log4J and is used under " +
                    "Apache License, version 2.0.  A copy of the license is at <a href=\"https://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\">Apache 2.0 License</a><br>" +
                    "The framework was rewritten from scratch and has been inspired by existing logging solutions, " +
                    "including Log4j 1 and java.util.logging. "),
    MARIADB(3, "MariaDb",
            " MariaDB is a community-developed, commercially supported fork of the MySQL relational database management system (RDBMS), " +
                    "intended to remain free and open-source software under the GNU General Public License. Development is led by some of " +
                    "the original developers of MySQL, who forked it due to concerns over its acquisition by Oracle Corporation in 2009"),
    JOOQ(4, "JOOQ",
            "The Service contains <a href=\"https://www.jooq.org/\" target=\"_blank\">JOOQ</a> and is used under " +
                    "Apache License, version 2.0.  A copy of the license is at <a href=\"https://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\">Apache 2.0 License</a><br>" +
                    "  jOOQ generates Java code from your database and lets you build type safe SQL queries through its fluent API "),
    JSTL(5, "JSTL",
            "Fast Development JSTL provides many tags that simplify the JSP.\n" +
            "Code Reusability We can use the JSTL tags on various pages.\n" +
            "No need to use scriptlet tag It avoids the use of scriptlet tag. "),
    APACHETOMCAT(6, "Apache Tomcat",
            "The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, " +
                    "Java Expression Language and Java WebSocket technologies. The Java Servlet, JavaServer Pages, Java Expression Language " +
                    "and Java WebSocket specifications are developed under the Java Community Process. "),
    OPENJDK(7, "OpenJDK",
            "OpenJDK, <a href=\"OpenJdk.java.net\" target=\"_blank\">OpenJdk.java.net</a>" +
                    " is a free and open-source implementation of the Java Platform, Standard Edition." +
                    " The implementation is licensed under the <a href=\"https://openjdk.java.net/legal/gplv2+ce.html\" target=\"_blank\">GNU General Public License (GNU GPL) version 2 with a linking exception</a>.");

    int id;
    String heading;
    String description ;

    AboutCollapsibleEnum(  int id,String heading, String description ){
       // GWT.log(("Creating SwitchProbeEnum"));
        this.heading = heading;
        this.id = id;
        this.description = description;
    }
}

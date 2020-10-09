rmdir /S /Q src\com\wbc\dashboarddatabase\generated
java -classpath ..\..\jars\jooq-3.3.1.jar;..\..\jars\jooq-meta-3.3.1.jar;..\..\jars\jooq-codegen-3.3.1.jar;..\..\jars\mysql-connector-java-5.1.7-bin.jar;.   org.jooq.util.GenerationTool /dashboard2_JOOQ.xml

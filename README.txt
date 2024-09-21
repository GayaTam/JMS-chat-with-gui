Francais/English

Francais : 

- Télécharger Active MQ (version 15.4 adéquate avec Java 7) à partir du 
lien suivant: https://activemq.apache.org/activemq-5016004-release
- Unzip le fichier zip téléchargé, copier le dans: C:\Program files ;
- Aller dans: C:\Program Files (x86)\apache-activemq-5.16.4\bin\win64 et 
ouvrir le terminal; taper activemq.bat start pour démarrer le broker
Tester le broker: ouvrir un navigateur web; taper l'url: 
http://localhost:8161/ ; utiliser admin admin si une fenêtre de connexion 
est affichée.
- Explorer les options offerts par cet outil.
- - Créer un projet Maven
- Configurer le compilateur utilisé et ajouter les dépendances au fichier de 
Maven: pom.xml
Il faut ajouter les dépendances de Active MQ et SLF4J pour pouvoir 
connecter les classes java avec le Broker. voici le contenu du pom.xml:
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
http://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>
 <groupId>org.sid</groupId>
 <artifactId>jmsMiddleware</artifactId>
 <version>0.0.1-SNAPSHOT</version>
 
 <properties>
 <maven.compiler.source>1.7</maven.compiler.source>
 <maven.compiler.target>1.7</maven.compiler.target>
 </properties>
 <dependencies>
<!--
https://mvnrepository.com/artifact/org.apache.activemq/activemqclient -->
<dependency>
 <groupId>org.apache.activemq</groupId>
 <artifactId>activemq-client</artifactId>
 <version>5.16.4</version>
</dependency>
<dependency>
 <groupId>org.slf4j</groupId>
 <artifactId>slf4j-jdk14</artifactId>
 <version>1.7.25</version>
</dependency>
</dependencies>
</project>

------------------------------------------------------------------ 
English : 
Here is the translation of your text into English:

- Download Active MQ (version 15.4 compatible with Java 7) from the following link: https://activemq.apache.org/activemq-5016004-release
- Unzip the downloaded zip file, and copy it into: `C:\Program files`;
- Go to: `C:\Program Files (x86)\apache-activemq-5.16.4\bin\win64`, open the terminal, and type `activemq.bat start` to start the broker.
Test the broker: open a web browser, and enter the URL: `http://localhost:8161/`; use `admin admin` if a login window appears.
- Explore the options offered by this tool.
- Create a Maven project.
- Configure the compiler and add the dependencies to the Maven file: `pom.xml`.
You need to add the dependencies for Active MQ and SLF4J to connect Java classes with the Broker. Here is the content of the `pom.xml`:
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
http://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>
 <groupId>org.sid</groupId>
 <artifactId>jmsMiddleware</artifactId>
 <version>0.0.1-SNAPSHOT</version>
 
 <properties>
 <maven.compiler.source>1.7</maven.compiler.source>
 <maven.compiler.target>1.7</maven.compiler.target>
 </properties>
 <dependencies>
<!--
https://mvnrepository.com/artifact/org.apache.activemq/activemqclient -->
<dependency>
 <groupId>org.apache.activemq</groupId>
 <artifactId>activemq-client</artifactId>
 <version>5.16.4</version>
</dependency>
<dependency>
 <groupId>org.slf4j</groupId>
 <artifactId>slf4j-jdk14</artifactId>
 <version>1.7.25</version>
</dependency>
</dependencies>
</project>



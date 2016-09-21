# ReleSIA = Release + [SIA](https://en.wikipedia.org/wiki/Sia_%28god%29) - a release overview and transparency management tool

### Uses:
1. [Hibernate](http://hibernate.org/)
2. [Jetty](http://www.eclipse.org/jetty/)
3. [Jersey - REST implementation](https://jersey.java.net/)
4. [Jackson - JSON support](https://github.com/FasterXML/jackson)


### **Initial set-up:**
1. **Database:**
    - **schema** and **user creation** SQL can be found in `dbscripts/createSchema.sql`
    - **tables creation** scripts can be found in `dbscripts/createTables.sql`

    *In case changes on the business model are done that require an update of the database, run the main class* `com.oneandone.relesia.tools.SQLScriptGenerator`. This class uses the model configurations from the hibernate config file `/db/hibernate.cfg.xml` to generate DDL to create and drop tables
in these files:

     * `dbscripts/createTables.sql`
     * `dbscripts/dropTables.sql`


2. **Start** the server **using** the class **com.oneandone.relesia.server.BEServer**.

    When started with no issues, access the REST API at: `http://localhost:18080/` e.g.

3. **Misc:**
    - `http://localhost:18080/tools/init`  => _generates some sample data in the database_

### **DataModel - resources:**
- `http://<host>:<port>/products` => _specifies a product, which can be composed of several applications (a.k.a. dev projects)_
- `http://<host>:<port>/applications` => _for applications, a.k.a. development projects_
- `http://<host>:<port>/issues` => _describes an issue discovered on an Application_
- `http://<host>:<port>/scms` => _describes a SCM_
- `http://<host>:<port>/buildTypes` => _describes a type of build that can be used for an Application_
- `http://<host>:<port>/plugins` => _describes a plugin that could be either a checker for the Applications, or a connector to various external tools. The plugin's description and various configurations are stored in the DB and an implementation must also be provided._
- `http://<host>:<port>/prlangs` =>  _describes a programming language that is used in an Application_ 
- `http://<host>:<port>/releaseRepos` => describes a Repository where an Application is released after build
- `http://<host>:<port>/users`- the users that can access this application

##### For each resource, the following actions are available:
- `GET <resourceURL>/list` => _get all resources in JSON format_
- `GET <resourceURL>/{id}` => _gets the specific resource with the specific id, in JSON format_
- `POST <resourceURL> + JSON format resource object` => _creates a new object_
- `PUT <resourceURL>  + JSON format resource object` => _updates the object, based on the id refference_
- `DELETE <resourceURL>/{id}` => _deletes the specific resource type with the specified id_

##### Authentication - still in progress. But, for now:
- All actions on all resources are accessible without authorization.
- **EXCEPT** for **User changes**. For the user changes (PUT, POST, DELETE), Basic **Authentication** is required.
- Currently only one **Admin user** is validated: `testuser:testpwd` 

###### Set-up https cert for SCMs connection:
- download the client certificate from the browser
- add the certificate to the java keystore using this command : ># sudo keytool -import -noprompt -trustcacerts -alias <aliasYouChoose> -file <pathToDownloadedCertificate> -keystore <$JAVA_HOME>/jre/lib/security/cacerts -storepass <yourKeystorePassword
- the default keystore password is 'changeit'

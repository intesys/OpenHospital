# OpenHospital
OpenHospital 2.0 (ISF OpenHospital web version) - WIP

**How to build with Maven:**
_(Requires JDK 7)_

    Install the Project Dependencies that are provided in the /lib sub-directory into your local maven's repository
    Other dependencies specified in the pom.xml file will be downloaded by Maven during the build process
    
**How to create the DataBase**:

You need a local (or remote) MySQL server where to run the script in mysql/db/ folder

	create_all_en.sql
	
For remote MySQL server you need to change:
- rsc/database.properties
- rsc/log4j.properties

**How to launch the software**:

Create an Environment Variable JAVA7_HOME and set its value to the path of the JDK7 installation on the Client
Deployment Directory, as produced by Maven, will be /target/OpenHospital20/
Use scripts OpenHospital.sh (Linux) or OpenHospital.cmd (Windows) located in the Deployment Directory

**Distributed Deployment(Remote Sql Server)**

Copy the directory folder remote_services/DbUpdateNotifier to your Server
Create an Environment Variable JAVA7_HOME and set its value to the path of the JDK7 installation on the Server
Configure the Windows Server to start the cmd file 'startup.cmd' on startup.(This will be a remote service used by the clients)

**Other info**

Please read Admin and User manuals in doc/ folder

# How to contribute

Please read the OpenHospital [Wiki](https://openhospital.atlassian.net/wiki/display/OH/Contribution+Guidelines)

See the Open Issues on [Jira](https://openhospital.atlassian.net/issues/)

## Webhack Demo

Small Spring Boot sample. To run the tests you will need to install mysql and create a database called `webhack`.

On Mac OS, this can be done with homebrew:

`brew install mysql`

`brew services start mysql`

`mysql -uroot`

Then execute this SQL: `CREATE DATABASE webhack;` 

To deploy on https://run.pivotal.io/, create an account, then:

1. `brew tap cloudfoundry/tap`

2. `brew install cf-cli`

3. `cf login`

4. Use `api.run.pivotal.io` as the API endpoint and login with your credentials.

5. After targeting your org, select a space you want to use or create one using `cf create space <name>`

6. Assemble the jar using `./gradlew assemble jar`

7. `cf push <your app name> -p build/libs/webhack-demo-0.0.1-SNAPSHOT.jar`

Consider using `--random-route` in the cf push command to avoid route conflicts. 

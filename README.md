# Project Manual

## Creating the Database

In your MySQL Instance of choice, run the `create_tables.sql` file found in `.\database` folder. Once completed, you can execute the statements in `data.sql` to insert some data into the database to test with it.

## Store Credentials in a `.env` file

Create a file named `db_credentials.env` in the project root.

`db_credentials.env` location:

``` plain
MyPDMProject
|-- pom.xml
|-- src
|   `-- main
|      `-- java
|          `-- com
|              `-- buspass
|                  `...
|-- target
|   `...
|-- .gitignore
|-- db_credentials.env
.
.
```

Inside `db_credentials.env`:

``` dotenv
DB_URL=jdbc:mysql://localhost:3306/digital_bus_pass
DB_USER=root
DB_PASSWORD=your_password_here
DB_MAX_POOL_SIZE=10
```

**Notes**:

- `DB_URL` should include the database name.
- `DB_USER` and `DB_PASSWORD` are your database JDBC URL and password, respectively.
- `DB_MAX_POOL_SIZE` is optional (default is 10).
- Make sure to include `db_credentials.env` in `.gitignore` to avoid committing secrets.

## Running the App

To run the application, navigate to the `Main` class from `com.buspass` project package, run the `main` method.

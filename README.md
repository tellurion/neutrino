# Neutrino

#### NOTE: Consider this vaporware until I actually deliver

Neutrino's aim is to remove as much boilerplate as possible in scala apps. It does this by combining dependency injection with auto-configuration. For example, if you want to use the Quill query library and a Postgres database, just add their dependencies and inject Quill's PostgresJdbcContext. Neutrino will see that you have not created a Postgres datasource and create an in memory Postgres instance, create and configure a datasource to use that instance, create and configure a Quill context to use that datasource, then inject it.

```
val ctx = inject[PostgresJdbcContext]
// you can now start using the fully configured Quill context
```



module de.olaf_roeder.eventsystem {
    // requires: runtime and compile-time dependencies
    requires org.slf4j;
    // requires static: compile-time-only dependencies
    requires static lombok;
    // requires transitive: transitive dependencies
    // exports: declare accessible API
    exports de.olaf_roeder.eventsystem.api;
    // export package to package: restrict access to one package
}
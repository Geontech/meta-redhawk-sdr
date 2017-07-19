
# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.

inherit autotools-brokensep pkgconfig pythonnative redhawk-entity


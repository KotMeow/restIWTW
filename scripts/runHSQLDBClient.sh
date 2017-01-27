#!/bin/sh

java -cp ./scripts/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing --url jdbc:hsqldb:hsql://localhost/workdb

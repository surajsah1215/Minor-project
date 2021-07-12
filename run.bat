set classpath=lib/twitter4j-async-4.0.2.jar;lib/twitter4j-media-support-4.0.2.jar;lib/twitter4j-stream-4.0.2.jar;lib/twitter4j-core-4.0.2.jar;lib/twitter4j-examples-4.0.2.jar;lib/jcommon-1.0.16.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13-swt.jar;lib/jfreechart-1.0.13.jar;lib/Panel.jar;.;
javac -d . *.java
java -Xmx1000M com.Main
pause
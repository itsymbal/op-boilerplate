package android.support.v4.app;

public class NonConfigurationInstanceHelper {

    /*
    * This utility class creates an instance of FragmentActivity.NonConfigurationInstances so it
    * can be saved as a NonConfigurationInstanceState. FragmentActivity expects the object to be of
    * type NonConfigurationInstances, but this inner class is package-scoped. Therefore this utility
    * method is placed in the same package as FragmentActivity.
    * */
    public static FragmentActivity.NonConfigurationInstances createCustomInstances(Object instance) {
        FragmentActivity.NonConfigurationInstances instances = new FragmentActivity
                .NonConfigurationInstances();
        instances.custom = instance;
        return instances;
    }
}

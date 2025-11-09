package ma.projet.restclient.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * RetrofitClient - Singleton configuration class for Retrofit HTTP client.
 * 
 * <p>
 * This class manages the creation and configuration of Retrofit instances
 * with support for both JSON and XML data formats. It implements a singleton
 * pattern to ensure efficient resource usage and prevents multiple Retrofit
 * instances from being created unnecessarily.
 * </p>
 * 
 * <p>
 * Features:
 * </p>
 * <ul>
 * <li>Singleton pattern implementation for resource efficiency</li>
 * <li>Dynamic converter selection (JSON/XML)</li>
 * <li>Automatic instance reuse when format hasn't changed</li>
 * <li>Support for Android emulator (10.0.2.2) localhost connection</li>
 * </ul>
 * 
 * <p>
 * Base URL: http://10.0.2.2:8082/ (Android emulator localhost mapping)
 * </p>
 * 
 * @author Mohamed
 * @version 1.0
 * @since 2025-11-09
 */
public class RetrofitClient {
    /** Singleton Retrofit instance */
    private static Retrofit retrofit = null;

    /** Current converter format (JSON or XML) */
    private static String currentFormat = null;

    /** Base URL for the REST API - Uses Android emulator localhost mapping */
    private static final String BASE_URL = "http://10.0.2.2:8082/";

    /**
     * Returns a configured Retrofit client instance.
     * Creates a new instance only if none exists or if the converter type has
     * changed.
     * 
     * @param converterType The data format to use ("JSON" or "XML")
     * @return A configured Retrofit instance
     */
    public static Retrofit getClient(String converterType) {
        // Check if existing Retrofit instance can be reused
        if (retrofit == null || !converterType.equals(currentFormat)) {
            currentFormat = converterType;
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL);

            // Add the appropriate converter factory based on the requested type
            if ("JSON".equals(converterType)) {
                builder.addConverterFactory(GsonConverterFactory.create());
            } else if ("XML".equals(converterType)) {
                builder.addConverterFactory(SimpleXmlConverterFactory.createNonStrict());
            }

            retrofit = builder.build();
        }
        return retrofit;
    }
}
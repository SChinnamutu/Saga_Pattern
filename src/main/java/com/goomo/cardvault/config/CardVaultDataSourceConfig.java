package com.goomo.cardvault.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.goomo.cardvault.constants.APPConstants;
import com.goomo.cardvault.model.DataSourceModel;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * This class used to create data source object for card vault related
 * properties.
 * 
 * @author Manjunath Jakkandi
 */
@Configuration
public class CardVaultDataSourceConfig {

	private static final Logger log = LoggerFactory.getLogger(CardVaultDataSourceConfig.class);

	@Value("${spring.profiles.active}")
	private String domain;

	@Value("${jwt.secret.key}")
	private String jwtSecretKey;
	
	@Value("${swagger.profile}")
	private String swaggerLibSwitch;
	
	@Value("${aes.secret.key}")
	private String aesSecretKey;
	
	@Autowired
	private Docket swaggerDocket;

	/**
	 * This method used to generate data source based upon domain.
	 * 
	 * @author Manjunath Jakkandi
	 * @return PEDataSourceModel
	 * @throws Exception
	 */
	@Bean
	public synchronized DataSourceModel getDataSource() throws Exception {
		// if domain type is dev -> read from property file. If domain type is prod,
		// read from system environment.
		log.info("domain type : " + domain);
		DataSourceModel dataSourceModel = null;
		if (domain != null && !domain.isEmpty() && domain.equalsIgnoreCase("dev")) {
			dataSourceModel = getDevelopmentDataSource();
		} else {
			dataSourceModel = getProductionDataSource();
		}
		log.info("Swagger Docket Profile :: "+swaggerDocket.isEnabled());
		return dataSourceModel;
	}

	/**
	 * This method used to generate the development data source.
	 * 
	 * @author Manjunath Jakkandi
	 * @return PEDataSourceModel
	 * @throws Exception
	 */
	private DataSourceModel getDevelopmentDataSource() throws Exception {
		DataSourceModel dataSourceModel = new DataSourceModel();
		dataSourceModel.setJwtSecretKey(jwtSecretKey);
		dataSourceModel.setAesSecretKey(aesSecretKey);
		if(swaggerLibSwitch!=null && !swaggerLibSwitch.isEmpty() && swaggerLibSwitch.equalsIgnoreCase("false")) {
			swaggerDocket.enable(false);
		}
		return dataSourceModel;
	}

	/**
	 * This method used to get the properties from environemnt properties if
	 * platform profile is production.
	 * 
	 * @author Manjunath Jakkandi
	 * @return PEDataSourceModel
	 * @throws Exception
	 */
	private DataSourceModel getProductionDataSource() throws Exception {
		DataSourceModel dataSourceModel = new DataSourceModel();
		Map<String, String> env = System.getenv();
		dataSourceModel.setJwtSecretKey(env.get(APPConstants.JWT_SECRET_KEY));
		dataSourceModel.setAesSecretKey(env.get(APPConstants.AES_SECRET_KEY));
		
		swaggerLibSwitch = env.get(APPConstants.SWAGGER_PROFILE);
		if(swaggerLibSwitch!=null && !swaggerLibSwitch.isEmpty() && swaggerLibSwitch.equalsIgnoreCase("false")) {
			swaggerDocket.enable(false);
		}
		return dataSourceModel;
	}
	

}

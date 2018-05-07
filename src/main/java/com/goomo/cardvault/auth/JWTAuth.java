package com.goomo.cardvault.auth;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.goomo.cardvault.constants.MessageCodes;
import com.goomo.cardvault.handlers.CustomException;


/**
 * This class used to authenticate, encrypt and decrypt user params with JWT HSA512 algorithm.
 * @author Manjunath Jakkandi
 *
 */
public class JWTAuth {
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuth.class);

	/**
	 * This interface used to define the list of claim keysrequired to generate JWT token.
	 * @author Manjunath Jakkandi
	 *
	 */
	public static interface JWTClaimKeys {
		// header
		String TYPE = "typ";
		String ALGORITHM = "alg";
		String OWNER = "owner";
		String KEY_ID = "kid";
		
		// create
		String ADMIN="admin";
		String NAME="name";
		String ISSUED_AT="iat";
		String EXPIRE_ON="exp";
		String SUBJECT="sub";
		String LOGIN_METHOD="login_method";
	}
	
	
	/**
	 * This interface used to define the list of claim values to generate JWT token.
	 * @author Manjunath Jakkandi
	 *
	 */
	public static interface JWTClaimValues {
		String JWT_ISSUER = "ynd-consult";
		String JWT_TYPE = "JWT";
		String JWT_OWNER = "GOOMO";
		String JWT_KEY_ID = "GOOMO-USER-ID";
		String JWT_ADMIN = "true";
		String JWT_LOGIN_METHOD = "Direct";
		String JWT_SUBJECT = "User Id";
		String JWT_NAME = "Goomo Card Vault";
	}
	
	/**
     * This method used to encrypt token using JWT HSA512 Algorithm
     * @author Manjunath Jakkandi
     * @param token
     * @return
     */
    public static String encryptToken(String token, String jwtSecretKey) {
    		log.info("Card Vault :: JWTAuth :: encryptToken :: token :: "+token);
    		String encryptedToken = null;
	    	try {
	    		Algorithm algo = Algorithm.HMAC512(jwtSecretKey);
	    		
	    		// header claims
	    		Map<String, Object> headerClaims = new HashMap<String, Object>();
	    		headerClaims.put(JWTClaimKeys.TYPE, JWTClaimValues.JWT_TYPE);
	    		headerClaims.put(JWTClaimKeys.OWNER, JWTClaimValues.JWT_OWNER);
	    		headerClaims.put(JWTClaimKeys.KEY_ID, JWTClaimValues.JWT_KEY_ID);
	    		
	    		// expiry date for jwt token
	    		Date expiryDate = new Date();
	    		int min = expiryDate.getMinutes()+60;
	    		System.out.println("expiryDate >>> "+expiryDate);
	    		expiryDate.setMinutes(min);
	    		System.out.println("expiryDate >>> "+expiryDate);
	    		
	    		// create jwt token
	    		encryptedToken = JWT.create()
	    				.withIssuer(JWTClaimValues.JWT_ISSUER)
	    				.withHeader(headerClaims)
	    				.withClaim(JWTClaimKeys.ADMIN, JWTClaimValues.JWT_ADMIN)
	    				.withClaim(JWTClaimKeys.NAME, JWTClaimValues.JWT_NAME)
	    				.withClaim(JWTClaimKeys.ISSUED_AT, new Date())
	    				.withClaim(JWTClaimKeys.EXPIRE_ON, expiryDate)
	    				.withClaim(JWTClaimKeys.SUBJECT, token)
	    				.withClaim(JWTClaimKeys.LOGIN_METHOD, JWTClaimValues.JWT_LOGIN_METHOD)
	    				.withJWTId(token)
	    				.sign(algo);
	    		log.info("Card Vault :: JWTAuth :: encryptToken :: encryptedToken :: "+encryptedToken);
	    	}catch(UnsupportedEncodingException e) {
	    		e.printStackTrace();
	    	}catch (JWTCreationException e1){
	    		e1.printStackTrace();
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    	}
    		return encryptedToken;
	}
    
    
    /**
     * This method used to decrypt JWT token using HSA512 Algorithm
     * @author Manjunath Jakkandi
     * @param token
     * @return
     * @throws Exception 
     */
    public static String decryptToken(String token, String jwtSecretKey) throws Exception{
    		String decodedToken = null;
    		try {
    			log.info("Card Vault :: JWTAuth :: decryptToken :: token  :: "+token);
    			Algorithm algo = Algorithm.HMAC512(jwtSecretKey);
    			JWTVerifier verifier = JWT.require(algo).build(); 
    			DecodedJWT jwt = verifier.verify(token);
    			decodedToken = jwt.getSubject();
    		}catch(TokenExpiredException e0) {
    			log.info("Card Vault :: JWTAuth :: decryptToken :: Token Expired :: "+token);
    			throw new CustomException(MessageCodes.UN_AUTHORISATION);
    		}catch(JWTVerificationException e2) {
    			log.info("Card Vault :: JWTAuth :: decryptToken :: JWTVerificationException :: "+e2.getMessage());
	    		throw new CustomException(MessageCodes.UN_AUTHORISATION);
	    	}catch(Exception e) {
	    		log.info("Card Vault :: JWTAuth :: decryptToken :: Internal Server Error ");
	    		throw new Exception(e.getMessage());
    		}
    		return decodedToken;
    }
    
    /*
	public static void main( String[] args ) throws Exception{
		String token="1234-5678-9012";
		String encryptedToken = encryptToken(token, "your-secret-key");
		String sampleEncodedToken = "eyJvd25lciI6IkdPT01PIiwidHlwIjoiSldUIiwiYWxnIjoiSFM1MTIiLCJraWQiOiJHT09NTy1VU0VSLUlEIn0.eyJzdWIiOiI3MjU1IiwibG9naW5fbWV0aG9kIjoiRGlyZWN0IiwiaXNzIjoieW5kLWNvbnN1bHQiLCJuYW1lIjoiR29vbW8gUGF5bWVudCBFbmdpbmUiLCJhZG1pbiI6InRydWUiLCJleHAiOjE1MTc0ODY5ODEsImlhdCI6MTUxNzQ4MzM4MSwianRpIjoiNzI1NSJ9.taeHtALGf8YiZ1PBrBcRyc-Pk9t2WcLZ_SrAbgQV1agTwtVxYTWj_AmUdSdEzLSyPUuvPMvRuZOWqig1nWO1bA";
		String dectoken = decryptToken(sampleEncodedToken, "your-secret-key");
		System.out.println("token >> "+dectoken);
		
	}
	*/
	
	
}

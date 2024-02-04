package com.java1234.utils;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;

import com.java1234.consts.JwtConsts;
import com.java1234.dto.JwtCheckResultDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * [ 靜態類別 ]: JWT 相關工具方法
 * @author aa349
 *
 */
public class JwtUtils {
	
	/**
	 * [ 公用方法 ]: 生成 JWT Token
	 * - id, subject 為帶入使用者名稱(username)
	 * @param username
	 * @return
	 */
	public static String genJwtToken(String username) {
		return createJWT(username, username, Long.valueOf(60*60*1000));
	}
	
	/**
	 * [ 公用方法 ]: 簽發 JWT
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 */
	public static String createJWT(String id, String subject, Long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generateKey();
		
		// [Step]: 設定 JWT 基本訊息
		JwtBuilder jwtBuilder = Jwts.builder()
				.setId(id)
				.setSubject(subject)                         // 設定主題
				.setIssuer("Java1234")                       // 設定發行者
				.setIssuedAt(now)                            // 發行時間
				.signWith(signatureAlgorithm, secretKey);    // 簽名方式、使用密鑰
				
		// [Step]: 設定 JWT 過期訊息
		if (ttlMillis >= 0) {
			Long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			jwtBuilder.setExpiration(expDate);   // 設定過期時間
		}
		
		return jwtBuilder.compact();
	}
	
	/**
	 * [ 公用方法 ]: 生成加密 KEY
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static SecretKey generateKey() {
		byte[] encodedKey = Base64.decode(JwtConsts.JWT_SECRET.getBytes());
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
	
	/**
	 * [ 公用方法 ]: 解析 JWT 字串
	 * @param jwt
	 * @return
	 */
	public static Claims parseJWT(String jwt) {
		SecretKey secretKey = generateKey();
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody();
	}

	public static JwtCheckResultDTO validateJWT(String token) {
		JwtCheckResultDTO checkResultDTO = new JwtCheckResultDTO();
		Claims claims = null;
		try {
			claims = parseJWT(token);
			checkResultDTO.setIsSuccess(true);
			checkResultDTO.setClaims(claims);
		} catch (ExpiredJwtException error) {    // JWT 過期
			checkResultDTO.setErrorCode(JwtConsts.JWT_ERROR_CODE_EXPIRE);
			checkResultDTO.setIsSuccess(false);
		} catch (SignatureException error) {    // JWT 驗證錯誤
			checkResultDTO.setErrorCode(JwtConsts.JWT_ERROR_CODE_FAIL);
			checkResultDTO.setIsSuccess(false);
		} catch (Exception error) {    // 其他錯誤
			checkResultDTO.setErrorCode(JwtConsts.JWT_ERROR_CODE_FAIL);
			checkResultDTO.setIsSuccess(false);
		}
		
		return checkResultDTO;
	}

}

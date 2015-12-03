package system.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jean
 */
public class                    Encoder
{
    public static String        encode(String mode, String key)
    { 
        byte[]                  uniqueKey = key.getBytes(); 
        byte[]                  hash = null; 
        StringBuilder           hashString = new StringBuilder(); 
        
        try
        {
            hash = MessageDigest.getInstance(mode).digest(uniqueKey); //MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("no " + mode + " support in this VM");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < hash.length; ++i)
        {
            String hex = Integer.toHexString(hash[i]); 
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length()-1));
            }
            else
                hashString.append(hex.substring(hex.length()-2));
        }
        return hashString.toString(); 
    }
}

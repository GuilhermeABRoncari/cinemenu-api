package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.ConfirmPasswordRecoveryResponseDto;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendHashCodeVerificationEmail(String email) {
        List<String> hashCodes = generateHashes(email);
        String htmlBody = """
                    <html>
                      <body style="margin:0; padding:0; background-color:#181818; font-family:sans-serif;">
                        <div style="width:100%%; display:flex; justify-content:center; align-items:center; padding:40px 0;">
                          <div style="width:90%%; max-width:600px; background-color:#181818; padding:32px; border-radius:16px; box-shadow:0 2px 24px #000a;">
                            <h1 style="color:#ff9100; text-align:center; margin:0 0 16px 0;">CineMenu</h1>
                            <h2 style="color:#ff9100; text-align:center; margin:0 0 24px 0;">Recuperação de Senha</h2>
                            <p style="color:#ff9100; text-align:center; font-size:18px; margin:0 0 12px 0;">Este é seu código de recuperação:</p>
                            <div style="text-align:center; margin-bottom:24px;">
                              <div style="display:inline-block; padding:12px 24px; background-color:#333; color:#ff9100; font-size:20px; font-weight:bold; border-radius:8px; word-break:break-all;">%s</div>
                            </div>
                            <p style="color:#aaa; text-align:center; font-size:13px; margin-bottom:8px;">Copie e cole o código acima no campo de recuperação da sua conta.</p>
                            <p style="color:#aaa; text-align:center; font-size:13px; margin-bottom:8px;">Se você não solicitou isso, ignore este e-mail.</p>
                            <p style="color:#555; text-align:center; font-size:12px;">Você não precisa responder este e-mail.</p>
                          </div>
                        </div>
                      </body>
                    </html>
                """.formatted(hashCodes.get(2));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail);
            helper.setTo(email);
            helper.setSubject("CineMenu - Email Verification");
            helper.setText(htmlBody, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private List<String> generateHashes(String email) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHH");
        List<String> hashes = new ArrayList<>();

        try {
            for (int i = 0; i <= 2; i++) {
                LocalDateTime time = LocalDateTime.now().plusHours(i);
                String formattedTime = time.format(formatter);

                String toHash = email + formattedTime;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                String fullHash = Base64.getUrlEncoder().encodeToString(encodedHash);

                hashes.add(fullHash.substring(0, 8).toUpperCase());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }

        return hashes;
    }

    public boolean isHashCodeValid(@Email @NotBlank String email, String receivedHash) {
        try {
            List<String> hashCodes = generateHashes(email);
            return hashCodes.contains(receivedHash);
        } catch (Exception e) {
            throw new RuntimeException("Error validating hash code", e);
        }
    }

    public ConfirmPasswordRecoveryResponseDto createResponse(String message, String email, String token) {
        return new ConfirmPasswordRecoveryResponseDto(message, email, token);
    }
}

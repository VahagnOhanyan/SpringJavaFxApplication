package ohanyan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import ohanyan.fw.Main;
import ohanyan.fw.auth.UserAuthDetails;
import ohanyan.fw.auth.UserAuthDetailsService;
import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import ohanyan.repo.*;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static ohanyan.controllers.AlertController.AlertType.INFO;

@RequiredArgsConstructor
@Component
@FxmlView("auth.fxml")
public class AuthController {
    private final FxWeaver fxWeaver;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserAuthDetailsService userAuthDetailsService;

    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    FxControllerAndView<AlertController, VBox> alertDialog;

    @FXML
    private TextField fldLogin;
    @FXML
    private PasswordField fldPass;
    @FXML
    private ImageView logoView;
    private final UserDetailsService userDetailsService;
    String userprofile = System.getenv("USERPROFILE");

    private static String host;
    private static String port;
    private static String dbName;

    private Stage stage;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {

        fldPass.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {

                saveLogin();
                authentication();
            }
        });
        /*InputStream is = null;
        try {
            is = this.getClass().getResource("/images/logo.png").openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image logo = new Image(is);
        logoView.setImage(logo);*/

        InputStream inp;
        Properties props = new Properties();

        try {
            inp = Files.newInputStream(Paths.get(userprofile + "\\SpringJavaFXApp\\config\\userLogin.ini"));
            props.load(inp);
            if (props.getProperty("user_login") != null) {
                fldLogin.setText(props.getProperty("user_login"));
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnLogin":
                saveLogin();
                authentication();
                break;

            case "btnExit":
                System.exit(0);
                break;
        }
    }

    private void saveLogin() {

        File propDir = new File(userprofile + "\\SpringJavaFXApp\\config");

        if (!propDir.exists()) {
            try {
                propDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        try {
            Properties properties = new Properties();
            properties.setProperty("user_login", fldLogin.getText());

            File file = new File(userprofile + "\\SpringJavaFXApp\\config\\userLogin.ini");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Autologin");
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentication() {

        if (!fldLogin.getText().equals("") && !fldPass.getText().equals("")) {
            Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findByUserLoginAndUserPass(fldLogin.getText(), fldPass.getText());
            if (userInfoEntity.isPresent()) {
                Optional<UserEntity> userEntity = userRepository.findByUserInfoId(userInfoEntity.get());
                if (userEntity.isPresent()) {
                    initRoles(fldLogin.getText());
                    MainController.role = userInfoEntity.get().getUserRoleId().getUserRoleName();
                    MainController.authorities = ContextController.authorities;
                    MainController.who = ContextController.who;
                    Main.authStage.close();
                } else {
                    setAlertStage("Authentication error", null, "Проверьте правильность ввода данных для аутентификации", INFO);
                }
            }
        } else {
            setAlertStage("Authentication error", null, "Введите данные для аутентификации", INFO);
        }
    }

    public void initRoles(String login) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);


        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        Map<String, Integer> authorities;
        authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .filter(e -> !e.getAuthority().startsWith("role_"))
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toMap(k -> k.substring(0, k.length() - 2), k -> {
                    int i = k.indexOf(":");
                    if (i > 0) {
                        return Integer.parseInt(k.substring(i + 1));
                    }
                    return 0;

                }));
        System.out.println(authorities);
        Map<String, Integer> roleAuthorities;
        roleAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .filter(e -> e.getAuthority().startsWith("role_"))
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toMap(k -> k.substring(5, k.length() - 2), k -> {
                    int i = k.indexOf(":");
                    if (i > 0) {
                        return Integer.parseInt(k.substring(i + 1));
                    }
                    return 0;

                }));
        System.out.println(roleAuthorities);
        Map<String, Integer> copyRoleAuthorities = new HashMap<>(roleAuthorities);
        for (Map.Entry<String, Integer> entry : authorities.entrySet()) {
            for (Map.Entry<String, Integer> role_entry : roleAuthorities.entrySet()) {
                if (entry.getKey().equals(role_entry.getKey())) {
                    copyRoleAuthorities.remove(role_entry.getKey());
                }
            }
        }

        ContextController.authorities = new HashMap<>();
        ContextController.authorities.putAll(authorities);
        ContextController.authorities.putAll(copyRoleAuthorities);


        UserAuthDetails userAuthDetails = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserInfoEntity> userInfo = userInfoRepository.findByUserLogin(userAuthDetails.getUsername());

        if (userInfo.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUserInfoId(userInfo.get());
            user.ifPresent(userEntity -> ContextController.who = userEntity.getUserFullname());
        }
        System.out.println("ContextController.who: " + ContextController.who);
    }

    private void setAlertStage(String title, String header, String content, AlertController.AlertType type) {
        alertDialog = fxWeaver.load(AlertController.class);
        AlertController controller = alertDialog.getController();
        controller.setTitle(title);
        controller.setHeader(header);
        controller.setContent(content);
        controller.show(title, header, content, type);
    }
}
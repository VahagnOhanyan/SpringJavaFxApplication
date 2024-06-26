package ohanyan.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import ohanyan.domain.*;
import ohanyan.domain.security.*;
import ohanyan.repo.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

@RequiredArgsConstructor
@Component
@FxmlView("access.fxml")
public class AccessController {
    private final PrivilegeService privilegeService;
    private final ModuleService moduleService;
    private final UserInfoService userInfoService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final AccessTypeService accessTypeService;
    private final AccessSectionService accessSectionService;
    private final RolePrivilegeService rolePrivilegeService;

    private final UserPrivilegeService userPrivilegeService;

    public TableView<AccessControls> referencesAccessView;
    public TableView<AccessControls> managementAccessView;
    public TableView<AccessControls> tasksAccessView;

    public TableView<AccessControls> timeSheetAccessView;
    public TableView<AccessControls> reportsAccessView;


    private final TableColumn<AccessControls, ComboBox<String>>[] refAccessControls = new TableColumn[7];
    private final TableColumn<AccessControls, ComboBox<String>>[] managementAccessControls = new TableColumn[7];
    private final TableColumn<AccessControls, ComboBox<String>>[] taskAccessControls = new TableColumn[7];
    private final TableColumn<AccessControls, ComboBox<String>>[] timeSheetAccessControls = new TableColumn[7];
    private final TableColumn<AccessControls, RadioButton>[] reportAccessControls = new TableColumn[2];
    private final ObservableList<AccessControls> refAccesslist = FXCollections.observableArrayList();
    private final ObservableList<AccessControls> managementAccesslist = FXCollections.observableArrayList();
    private final ObservableList<AccessControls> taskAccesslist = FXCollections.observableArrayList();

    private final ObservableList<AccessControls> timeSheetAccesslist = FXCollections.observableArrayList();
    private final ObservableList<AccessControls> reportAccesslist = FXCollections.observableArrayList();
    private final ObservableList<String> availableValues = FXCollections.observableArrayList("Yes", "No");
    private final ObservableList<String> availableValuesForConfirm =
            FXCollections.observableArrayList("Leading specialist", "Department head", "Project manager", "Super_user", "No");
    private final ObservableList<String> availableValuesForRequestConfirm =
            FXCollections.observableArrayList("АУП", "Project manager", "Super_user", "No");


    private ObservableList<String> availableRoles = null;

    @FXML
    public TitledPane refLabel;
    @FXML
    public TitledPane managementLabel;
    @FXML
    public TitledPane taskLabel;
    @FXML
    private TitledPane timeSheetLabel;
    @FXML
    public TitledPane reportLabel;


    @FXML
    AnchorPane accessControlPane;
    @FXML
    public Button apply;
    @FXML
    public Button cancel;
    @FXML
    public Button close;
    @FXML
    public Button reset;
    @FXML
    public ComboBox<String> roleBox;

    List<UserRoleEntity> roleList;

    private Stage stage;
    private String login = "";

    private String role = "";

    Map<Integer, AccessTypeEntity> accessTypeList;

    private static int counter = 0;
    private String userFullName;
    private String userLogin;
    private List<PrivilegeEntity> rPrivilegeList;

    @FXML
    private void initialize() {

        accessTypeList = new ArrayList<>(accessTypeService.findAll()).stream().collect(Collectors.toMap(AccessTypeEntity::getAccessTypeId, k -> k));
        roleList = userRoleService.findAll().stream().sorted().collect(Collectors.toList());
        availableRoles = FXCollections.observableArrayList();
        for (UserRoleEntity r :
                roleList) {
            availableRoles.add(r.getUserRoleName());
        }
        roleBox.getItems().addAll(availableRoles);
        if (!role.equals("")) {
            roleBox.setValue(role);
        }
        refAccesslist.clear();
        managementAccesslist.clear();
        taskAccesslist.clear();
        reportAccesslist.clear();
        stage = new Stage();
        stage.setScene(new Scene(accessControlPane));
        stage.setMinHeight(100);
        stage.setMinWidth(100);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        roleBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fillAccessControls(newValue));
    }

    public void show(String login) {
        this.login = login;

        if (login.equals("")) {
            stage.setTitle("Access settings");
        } else {
            Optional<UserInfoEntity> u = userInfoService.findByUserLogin(login);
            if (u.isPresent()) {
                Optional<UserEntity> user = userService.findByUserInfoId(u.get());
                if (user.isPresent()) {
                    userFullName = user.get().getUserFullname();
                    userLogin = user.get().getUserInfoId().getUserLogin();
                    role = user.get().getUserInfoId().getUserRoleId().getUserRoleName();
                }
            }
            stage.setTitle("Access settings >>> " + role + " >>> " + userFullName);
        }
        if (!role.equals("")) {
            roleBox.setValue(role);
            fillAccessControls(roleBox.getValue());
        }

        reset.setVisible(!login.equals(""));
        stage.show();
    }

    private void fillAccessControls(String newValue) {
        if (newValue != null && !newValue.equals("Choose role")) {
            if (newValue.equals("apply")) {
                role = roleBox.getValue();
            }
            if (!role.equals("") && (newValue.equals("reset") || newValue.equals("cancel")) && !login.equals("")) {
                roleBox.setValue(role);
            }
            roleList = userRoleService.findAll();
            rPrivilegeList = roleList.stream().filter(r -> r.getUserRoleName().equals(role))
                    .flatMap(k -> k.getPrivileges().stream())
                    .collect(Collectors.toList());
            referencesAccessView.setVisible(true);
            managementAccessView.setVisible(true);
            tasksAccessView.setVisible(true);
            timeSheetAccessView.setVisible(true);
            referencesAccessView.setVisible(true);

            refLabel.setVisible(true);
            managementLabel.setVisible(true);
            taskLabel.setVisible(true);
            timeSheetLabel.setVisible(true);
            reportLabel.setVisible(true);

            apply.setVisible(true);
            reset.setVisible(true);

            if (login.equals("")) {
                stage.setTitle("Access settings  >>>  " + roleBox.getValue());
            } else {
                stage.setTitle("Access settings >>> " + roleBox.getValue() + " >>> " + userFullName);
            }
            initAccessRights("Directories", referencesAccessView, refAccessControls, null, refAccesslist);
            initAccessRights("Management", managementAccessView, managementAccessControls, null, managementAccesslist);
            initAccessRights("Tasks", tasksAccessView, taskAccessControls, null, taskAccesslist);
            initAccessRights("Timesheet", timeSheetAccessView, timeSheetAccessControls, null, timeSheetAccesslist);
            initAccessRights("Reports", reportsAccessView, null, reportAccessControls, reportAccesslist);
        } else {
            referencesAccessView.setVisible(false);
            managementAccessView.setVisible(false);
            tasksAccessView.setVisible(false);
            timeSheetAccessView.setVisible(false);
            referencesAccessView.setVisible(false);
            refLabel.setVisible(false);
            managementLabel.setVisible(false);
            taskLabel.setVisible(false);
            timeSheetLabel.setVisible(false);
            reportLabel.setVisible(false);
            apply.setVisible(false);
            reset.setVisible(false);
        }
    }


    private void initAccessRights(String subject, TableView<AccessControls> accessView, TableColumn<AccessControls, ComboBox<String>>[] accessControls, TableColumn<AccessControls, RadioButton>[] accessRadioButtons, ObservableList<AccessControls> accessList) {
        accessView.getColumns().clear();
        accessList.clear();
        accessView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<AccessControls, ?>> columns = accessView.getColumns();
        TableColumn<AccessControls, String> tableColumn = new TableColumn<>(subject);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumn.setStyle("-fx-alignment: CENTER;");
        tableColumn.setSortable(false);
        columns.add(tableColumn);
        Optional<AccessSectionEntity> accessSection = accessSectionService.findByAccessSectionName(subject);
        if (accessSection.isPresent()) {
            AccessSectionEntity accessSectionEntity = accessSection.get();

            if (accessSectionEntity.getAccessSectionName().equals("Directories") ||
                    accessSectionEntity.getAccessSectionName().equals("Management") ||
                    accessSectionEntity.getAccessSectionName().equals("Tasks") ||
                    accessSectionEntity.getAccessSectionName().equals("Timesheet")) {
                accessView.setItems(accessList);
                fillAccessRightsForm1(accessControls);
                Collections.addAll(columns, accessControls);
                setAccessRights(accessList, accessSectionEntity);
            }

            if (accessSectionEntity.getAccessSectionName().equals("Reports")) {
                accessView.setItems(accessList);
                fillAccessRightsForm2(accessRadioButtons);
                Collections.addAll(columns, accessRadioButtons);
                setAccessRights(accessList, accessSectionEntity);
            }
        }
    }

    private void fillAccessRightsForm1(TableColumn<AccessControls, ComboBox<String>>[] accessControls) {
        accessControls[0] = new TableColumn<>("Visibility");
        accessControls[0].setCellValueFactory(new PropertyValueFactory<>("folderVisibility"));
        accessControls[1] = new TableColumn<>("Detail");
        accessControls[1].setCellValueFactory(new PropertyValueFactory<>("detailedInfo"));
        accessControls[2] = new TableColumn<>("Create");
        accessControls[2].setCellValueFactory(new PropertyValueFactory<>("create"));
        accessControls[3] = new TableColumn<>("Edit");
        accessControls[3].setCellValueFactory(new PropertyValueFactory<>("edit"));
        accessControls[4] = new TableColumn<>("Delete");
        accessControls[4].setCellValueFactory(new PropertyValueFactory<>("delete"));
        accessControls[5] = new TableColumn<>("Assign");
        accessControls[5].setCellValueFactory(new PropertyValueFactory<>("assign"));
        accessControls[6] = new TableColumn<>("Adjustment");
        accessControls[6].setCellValueFactory(new PropertyValueFactory<>("confirm"));

    }

    private void fillAccessRightsForm2(TableColumn<AccessControls, RadioButton>[] accessControls) {
        accessControls[0] = new TableColumn<>("Yes");
        accessControls[0].setCellValueFactory(new PropertyValueFactory<>("yes"));
        accessControls[1] = new TableColumn<>("No");
        accessControls[1].setCellValueFactory(new PropertyValueFactory<>("no"));

    }

    private void setAccessRights(ObservableList<AccessControls> accessList, AccessSectionEntity accessSection) {
        List<ModuleEntity> moduleList = moduleService.findByAccessSectionId(accessSection);
        List<PrivilegeEntity> privilegeListPerModule;
        for (ModuleEntity module : moduleList) {
            Optional<UserRoleEntity> userRole = userRoleService.findByUserRoleName(roleBox.getValue());
            if (userRole.isPresent()) {
                Set<PrivilegeEntity> rolePrivilegeList = userRole.get().getPrivileges();
                if (!login.equals("") && role.equals(roleBox.getValue())) {
                    Optional<UserInfoEntity> userInfoEntity = userInfoService.findByUserLogin(userLogin);
                    if (userInfoEntity.isPresent()) {
                        Set<PrivilegeEntity> userPrivilegeList = userInfoEntity.get().getPrivileges();
                        privilegeListPerModule = new ArrayList<>();
                        for (PrivilegeEntity userPrivilege : userPrivilegeList) {
                            if (module.getModuleId() == userPrivilege.getModuleID().getModuleId()) {
                                privilegeListPerModule.add(userPrivilege);
                            }
                        }
                        out:
                        for (PrivilegeEntity rolePrivilege :
                                rolePrivilegeList) {
                            boolean existsInUserRoles = false;
                            for (PrivilegeEntity userPrivilege : userPrivilegeList) {
                                if (userPrivilege.getPrivilegeName().startsWith(cutPostFix(rolePrivilege.getPrivilegeName()))) {
                                    existsInUserRoles = true;
                                    break;
                                }
                            }
                            if (!existsInUserRoles) {
                                if (module.getModuleId() == rolePrivilege.getModuleID().getModuleId()) {
                                    privilegeListPerModule.add(rolePrivilege);
                                }
                            }
                        }
                        accessList.add(new AccessControls(privilegeListPerModule, module));
                    }
                } else {
                    for (UserRoleEntity r : roleList) {
                        if (r.getUserRoleName().equalsIgnoreCase(roleBox.getValue())) {
                            privilegeListPerModule = new ArrayList<>();
                            for (PrivilegeEntity rolePrivilege :
                                    r.getPrivileges()) {
                                if (module.getModuleId() == rolePrivilege.getModuleID().getModuleId()) {
                                    privilegeListPerModule.add(rolePrivilege);
                                }
                            }
                            accessList.add(new AccessControls(privilegeListPerModule, module));
                        }
                    }

                }
            }
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "apply":
                counter = 0;
                for (AccessControls access :
                        refAccesslist) {
                    updatePrivilege(access);

                }
                for (AccessControls access :
                        managementAccesslist) {
                    updatePrivilege(access);

                }
                for (AccessControls access :
                        taskAccesslist) {
                    updatePrivilege(access);

                }
                for (AccessControls access :
                        timeSheetAccesslist) {
                    updatePrivilege(access);
                }
                for (AccessControls access :
                        reportAccesslist) {
                    updatePrivilege(access);
                }
                fillAccessControls("apply");
                break;
            case "cancel":
                fillAccessControls("cancel");
                break;
            case "close":
                stage.close();
                break;
            case "reset":
                Optional<UserInfoEntity> user = userInfoService.findByUserLogin(userLogin);
                if (user.isPresent()) {
                    List<UserPrivilegeEntity> userPrivileges = userPrivilegeService.findByUserInfoId(user.get());
                    userPrivilegeService.deleteAll(userPrivileges);
                    fillAccessControls("reset");
                }
                break;
        }
    }

    public void updatePrivilege(AccessControls access) {
        if (!login.equals("")) {
            Optional<UserInfoEntity> user = userInfoService.findByUserLogin(userLogin);
            if (user.isPresent()) {
                if (counter == 0) {
                    List<UserPrivilegeEntity> userPrivileges = userPrivilegeService.findByUserInfoId(user.get());
                    userPrivilegeService.deleteAll(userPrivileges);
                    ++counter;
                }

                for (PrivilegeEntity p :
                        access.getPrivilegeList()) {
                    for (PrivilegeEntity pe :
                            rPrivilegeList) {
                        if (p.getPrivilegeName().contains(pe.getPrivilegeName()) && p.getAccessTypeId().getAccessTypeId() != pe.getAccessTypeId().getAccessTypeId()) {
                            userPrivilegeService.save(new UserPrivilegeEntity(p, user.get()));
                            break;
                        }
                    }

                }
            }
            Optional<UserRoleEntity> userRole = userRoleService.findByUserRoleName(roleBox.getValue());
            userRole.ifPresent(userRoleEntity -> userInfoService.updateUserRole(userRoleEntity, userLogin));
        } else {
            Optional<UserRoleEntity> userRole = userRoleService.findByUserRoleName(roleBox.getValue());
            if (userRole.isPresent()) {
                if (counter == 0) {
                    List<RolePrivilegeEntity> rolePrivileges = rolePrivilegeService.findByUserRoleId(userRole.get());
                    rolePrivilegeService.deleteAll(rolePrivileges);
                    ++counter;
                }

                for (PrivilegeEntity p :
                        access.getPrivilegeList()) {
                    rolePrivilegeService.save(new RolePrivilegeEntity(p, userRole.get()));
                }

            }
        }
    }


    private int getAccessTypeId(String accessType) {
        if (accessType.equals("Yes")) {
            return 1;
        }
        if (accessType.equals("Leading specialist")) {
            return 1;
        }
        if (accessType.equals("Department head")) {
            return 1;
        }
        if (accessType.equals("Project manager")) {
            return 1;
        }
        if (accessType.equals("АУП")) {
            return 1;
        }
        if (accessType.equals("Super_user")) {
            return 1;
        }
        if (accessType.equals("No")) {
            return 2;
        }
        return 0;
    }

    private int getAccessTypeIdByBoolean(boolean accessType) {
        if (accessType) {
            return 1;
        }
        return 2;
    }

    private String cutPostFix(String privilegeName) {
        if (privilegeName.endsWith("Super_user")) {
            return privilegeName.replace("Super_user", "");
        }
        if (privilegeName.endsWith("AUP")) {
            return privilegeName.replace("AUP", "");
        }
        if (privilegeName.endsWith("PM")) {
            return privilegeName.replace("PM", "");
        }
        if (privilegeName.endsWith("DH")) {
            return privilegeName.replace("DH", "");
        }
        if (privilegeName.endsWith("LE")) {
            return privilegeName.replace("LE", "");
        }
        return privilegeName;
    }

    public class AccessControls {
        private List<PrivilegeEntity> privilegeList;
        private final String name;

        private final ComboBox<String> folderVisibility;
        private final ComboBox<String> detailedInfo;
        private String defaultDetailedInfoValue = "";
        private final ComboBox<String> create;
        private String defaultCreateValue = "";
        private final ComboBox<String> edit;
        private String defaultEditValue = "";
        private final ComboBox<String> delete;
        private String defaultDeleteValue = "";
        private String defaultConfirmValue = "";
        private final ComboBox<String> assign;
        private String defaultAssignValue;
        private final RadioButton yes;
        private final RadioButton no;
        private final ComboBox<String> confirm;

        public AccessControls(List<PrivilegeEntity> pList, ModuleEntity module) {
            privilegeList = pList;
            name = module.getModuleName();
            ToggleGroup group = new ToggleGroup();
            yes = new RadioButton();
            no = new RadioButton();
            yes.setToggleGroup(group);
            no.setToggleGroup(group);
            folderVisibility = new ComboBox<>(availableValues);
            detailedInfo = new ComboBox<>(availableValues);
            create = new ComboBox<>(availableValues);
            edit = new ComboBox<>(availableValues);
            delete = new ComboBox<>(availableValues);
            assign = new ComboBox<>(availableValues);
            // confirm = new ComboBox<>(availableValues);
            if (login.equals("")) {
                confirm = new ComboBox<>(availableValues);
            } else {
                if (module.getModuleName().equals("Timesheet")) {
                    confirm = new ComboBox<>(availableValuesForConfirm);
                } else if (module.getModuleName().equals("Requests")) {
                    confirm = new ComboBox<>(availableValuesForRequestConfirm);
                } else {
                    confirm = new ComboBox<>(availableValues);
                }
            }
            for (PrivilegeEntity privilege :
                    pList) {
                if (privilege.getPrivilegeName().startsWith("report")) {
                    yes.setSelected(getAccessTypeBooleanValue(privilege.getAccessTypeId().getAccessTypeId()));
                    no.setSelected(!getAccessTypeBooleanValue(privilege.getAccessTypeId().getAccessTypeId()));
                }
                if (privilege.getPrivilegeName().startsWith("confirm")) {
                    if (login.equals("")) {
                        confirm.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    } else {
                        confirm.setValue(getAccessTypeValueByRole(privilege));
                    }
                    defaultConfirmValue = confirm.getValue();

                }
                if (privilege.getPrivilegeName().startsWith("read")) {
                    folderVisibility.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    if (folderVisibility.getValue().equals("No")) {
                        detailedInfo.setValue(folderVisibility.getValue());
                        detailedInfo.setDisable(true);
                        edit.setValue(folderVisibility.getValue());
                        edit.setDisable(true);
                        delete.setValue(folderVisibility.getValue());
                        delete.setDisable(true);
                        assign.setValue(folderVisibility.getValue());
                        assign.setDisable(true);
                        confirm.setValue(folderVisibility.getValue());
                        confirm.setDisable(true);

                    }
                }
                if (privilege.getPrivilegeName().startsWith("view")) {
                    detailedInfo.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    defaultDetailedInfoValue = detailedInfo.getValue();
                }
                if (privilege.getPrivilegeName().startsWith("create")) {
                    create.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    defaultCreateValue = create.getValue();
                }
                if (privilege.getPrivilegeName().startsWith("edit")) {
                    edit.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    defaultEditValue = edit.getValue();
                }
                if (privilege.getPrivilegeName().startsWith("delete")) {
                    delete.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    defaultDeleteValue = delete.getValue();
                }

                if (privilege.getPrivilegeName().startsWith("assign")) {
                    assign.setValue(getAccessTypeValue(privilege.getAccessTypeId().getAccessTypeId()));
                    defaultAssignValue = assign.getValue();
                }
            }

            if (!yes.isSelected() && !no.isSelected() && module.getAccessSectionId().getAccessSectionId() == 4) {
                no.setSelected(true);
                saveAbsentPrivilege("report", module, 2);
            }
            if (folderVisibility.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                folderVisibility.setValue("No");
                saveAbsentPrivilege("read", module, 2);

                detailedInfo.setValue(folderVisibility.getValue());
                detailedInfo.setDisable(true);
                defaultDetailedInfoValue = detailedInfo.getValue();
                saveAbsentPrivilege("view", module, 2);

                edit.setValue(folderVisibility.getValue());
                edit.setDisable(true);
                defaultEditValue = edit.getValue();
                saveAbsentPrivilege("edit", module, 2);

                delete.setValue(folderVisibility.getValue());
                delete.setDisable(true);
                defaultDeleteValue = delete.getValue();
                saveAbsentPrivilege("delete", module, 2);

                assign.setValue(folderVisibility.getValue());
                assign.setDisable(true);
                defaultAssignValue = assign.getValue();
                saveAbsentPrivilege("assign", module, 2);

                confirm.setValue(folderVisibility.getValue());
                confirm.setDisable(true);
                defaultAssignValue = assign.getValue();
                saveAbsentPrivilege("confirm", module, 2);
            }

            if (detailedInfo.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                detailedInfo.setValue("No");
                defaultDetailedInfoValue = detailedInfo.getValue();
                saveAbsentPrivilege("view", module, 2);
            }
            if (create.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                create.setValue("No");
                defaultCreateValue = create.getValue();
                saveAbsentPrivilege("create", module, 2);
            }
            if (edit.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                edit.setValue("No");
                defaultEditValue = edit.getValue();
                saveAbsentPrivilege("edit", module, 2);
            }
            if (delete.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                delete.setValue("No");
                defaultDeleteValue = delete.getValue();
                saveAbsentPrivilege("delete", module, 2);
            }
            if (assign.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                assign.setValue("No");
                defaultAssignValue = assign.getValue();
                saveAbsentPrivilege("assign", module, 2);
            }
            if (confirm.getValue() == null && (module.getAccessSectionId().getAccessSectionId() != 4)) {
                confirm.setValue("No");
                defaultConfirmValue = confirm.getValue();
                saveAbsentPrivilege("confirm", module, 2);

            }
            confirm.setVisible(false);
            assign.setVisible(false);
            if (module.getModuleName().equals("Timesheet") &&
                    (roleBox.getValue().equals("Leading specialist") ||
                            roleBox.getValue().equals("Project manager") ||
                            roleBox.getValue().equals("Department head") ||
                            roleBox.getValue().equals("Super_user") || !login.equals(""))) {
                confirm.setVisible(true);
            }
            if (FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Request") &&
                    (roleBox.getValue().equals("АУП") ||
                            roleBox.getValue().equals("Project manager") ||
                            roleBox.getValue().equals("Super_user") || !login.equals(""))) {
                confirm.setVisible(true);
            }

            if (FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Customer") ||
                    FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Project") ||
                    FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Request") ||
                    FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Employee") ||
                    FxmlViewAccessController.getTranslatedValue(module.getModuleName()).equals("Task")) {
                assign.setVisible(true);
            }

            folderVisibility.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    folderVisibility.setValue(newValue);
                    if (newValue.equals("No")) {
                        detailedInfo.setValue(newValue);
                        detailedInfo.setDisable(true);
                        edit.setValue(newValue);
                        edit.setDisable(true);
                        delete.setValue(newValue);
                        delete.setDisable(true);
                        assign.setValue(newValue);
                        assign.setDisable(true);
                        confirm.setValue(newValue);
                        confirm.setDisable(true);
                    } else {
                        detailedInfo.setValue(defaultDetailedInfoValue);
                        detailedInfo.setDisable(false);
                        edit.setValue(defaultEditValue);
                        edit.setDisable(false);
                        delete.setValue(defaultDeleteValue);
                        delete.setDisable(false);
                        assign.setValue(defaultAssignValue);
                        assign.setDisable(false);
                        confirm.setValue(defaultAssignValue);
                        confirm.setDisable(false);
                    }

                    getPrivilege("read", module, getAccessTypeId(newValue));

                }
            });
            detailedInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    detailedInfo.setValue(newValue);
                    getPrivilege("view", module, getAccessTypeId(newValue));

                }
            });

            create.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    create.setValue(newValue);
                    getPrivilege("create", module, getAccessTypeId(newValue));

                }
            });
            edit.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    edit.setValue(newValue);
                    getPrivilege("edit", module, getAccessTypeId(newValue));

                }
            });
            delete.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    delete.setValue(newValue);
                    getPrivilege("delete", module, getAccessTypeId(newValue));

                }
            });
            assign.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    assign.setValue(newValue);
                    getPrivilege("assign", module, getAccessTypeId(newValue));

                }
            });
            confirm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    confirm.setValue(newValue);
                    getPrivilege("confirm", module, getAccessTypeId(newValue));

                }
            });

            yes.selectedProperty().addListener(observable -> {
                if (yes.isSelected()) {
                    getPrivilege("report", module, getAccessTypeIdByBoolean(true));
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
            });
            no.selectedProperty().addListener(observable -> {
                if (no.isSelected()) {
                    getPrivilege("report", module, getAccessTypeIdByBoolean(false));
                    System.out.println("No");
                } else {
                    System.out.println("Yes");
                }
            });
        }


        private void getPrivilege(String prefix, ModuleEntity module, int at) {
            String privilegeNameWithOutPostFix = prefix + FxmlViewAccessController.getTranslatedValue(module.getModuleName());
            String privilegeName = prefix + FxmlViewAccessController.getTranslatedValue(module.getModuleName()) + getPostFix(prefix, FxmlViewAccessController.getTranslatedValue(module.getModuleName()));
            AccessTypeEntity accessType = accessTypeList.get(at);
            Optional<PrivilegeEntity> privilegeEntity = privilegeService.findByPrivilegeNameAndAccessTypeId(privilegeName, accessType);

            if (!privilegeEntity.isPresent()) {
                privilegeService.save(new PrivilegeEntity(privilegeName, accessType, module));
                privilegeEntity = privilegeService.findByPrivilegeNameAndAccessTypeId(privilegeName, accessType);

            }
            if (privilegeEntity.isPresent()) {
                privilegeList = privilegeList.stream().filter(v -> !v.getPrivilegeName().contains(privilegeNameWithOutPostFix)).collect(Collectors.toList());
                privilegeList.add(privilegeEntity.get());
            }
        }

        private void saveAbsentPrivilege(String prefix, ModuleEntity module, int at) {
            String privilegeNameWithOutPostFix = prefix + FxmlViewAccessController.getTranslatedValue(module.getModuleName());
            String privilegeName = prefix + FxmlViewAccessController.getTranslatedValue(module.getModuleName()) + getPostFix(prefix, FxmlViewAccessController.getTranslatedValue(module.getModuleName()));

            Optional<PrivilegeEntity> privilegeEntity = privilegeService.findByPrivilegeNameAndAccessTypeId(privilegeName, accessTypeList.get(2));
            if (!privilegeEntity.isPresent()) {
                privilegeService.save(new PrivilegeEntity(privilegeName, accessTypeList.get(2), module));
                privilegeEntity = privilegeService.findByPrivilegeNameAndAccessTypeId(privilegeName, accessTypeList.get(2));

            }
            if (privilegeEntity.isPresent()) {
                privilegeList = privilegeList.stream().filter(v -> !v.getPrivilegeName().equals(privilegeNameWithOutPostFix)).collect(Collectors.toList());
                privilegeList.add(privilegeEntity.get());
            }
            privilegeEntity = privilegeService.findByPrivilegeNameAndAccessTypeId(privilegeName, accessTypeList.get(1));
            if (!privilegeEntity.isPresent()) {
                privilegeService.save(new PrivilegeEntity(privilegeName, accessTypeList.get(1), module));
            }

        }

        private String getPostFix(String prefix, CharSequence module) {
            if (prefix.equals("confirm") && module.equals("TimeSheet")) {
                if (roleBox.getValue().equals("Leading specialist")) {
                    return "LE";
                }
                if (roleBox.getValue().equals("Department head")) {
                    return "DH";
                }
                if (roleBox.getValue().equals("Project manager")) {
                    return "PM";
                }
                if (roleBox.getValue().equals("Super_user")) {
                    return "Super_user";
                }
                if (!login.equals("") && !confirm.getValue().equals("No")) {
                    if (confirm.getValue().equals("Leading specialist")) {
                        return "LE";
                    }
                    if (confirm.getValue().equals("Department head")) {
                        return "DH";
                    }
                    if (confirm.getValue().equals("Project manager")) {
                        return "PM";
                    }
                    if (confirm.getValue().equals("Super_user")) {
                        return "Super_user";
                    }
                    return "";
                }
            }
            if (prefix.equals("confirm") && module.equals("Request")) {
                if (roleBox.getValue().equals("AUP")) {
                    return "AUP";
                }
                if (roleBox.getValue().equals("Project manager")) {
                    return "PM";
                }
                if (roleBox.getValue().equals("Super_user")) {
                    return "Super_user";
                }
                if (!login.equals("") && !confirm.getValue().equals("No")) {
                    if (confirm.getValue().equals("AUP")) {
                        return "AUP";
                    }
                    if (confirm.getValue().equals("Project manager")) {
                        return "PM";
                    }
                    if (confirm.getValue().equals("Super_user")) {
                        return "Super_user";
                    }
                    return "";
                }
            }
            return "";
        }


        private String getAccessTypeValue(int accessTypeId) {
            if (accessTypeId == 1) {
                return "Yes";
            }
            if (accessTypeId == 2) {
                return "No";

            }
            return "Undefined";
        }

        private String getAccessTypeValueByRole(PrivilegeEntity privilege) {
            if (privilege.getAccessTypeId().getAccessTypeId() == 1) {
                if (privilege.getPrivilegeName().endsWith("LE")) {
                    return "Leading specialist";
                }
                if (privilege.getPrivilegeName().endsWith("DH")) {
                    return "Department head";
                }
                if (privilege.getPrivilegeName().endsWith("PM")) {
                    return "Project manager";
                }
                if (privilege.getPrivilegeName().endsWith("Super_user")) {
                    return "Super_user";
                }
                if (privilege.getPrivilegeName().endsWith("AUP")) {
                    return "АУП";
                }
            }
            if (privilege.getAccessTypeId().getAccessTypeId() == 2) {
                return "No";
            }
            return "Undefined";
        }

        private boolean getAccessTypeBooleanValue(int accessTypeId) {
            return accessTypeId == 1;
        }

        public String getName() {
            return name;
        }

        public ComboBox<String> getFolderVisibility() {
            return folderVisibility;
        }

        public ComboBox<String> getDetailedInfo() {
            return detailedInfo;
        }

        public ComboBox<String> getCreate() {
            return create;
        }

        public ComboBox<String> getEdit() {
            return edit;
        }

        public ComboBox<String> getDelete() {
            return delete;
        }

        public ComboBox<String> getAssign() {
            return assign;
        }

        public List<PrivilegeEntity> getPrivilegeList() {
            return privilegeList;
        }


        public RadioButton getYes() {
            return yes;
        }

        public RadioButton getNo() {
            return no;
        }

        public ComboBox<String> getConfirm() {
            return confirm;
        }

    }

}
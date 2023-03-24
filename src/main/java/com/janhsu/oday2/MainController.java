package com.janhsu.oday2;

import com.janhsu.oday2.controllers.*;
import com.janhsu.oday2.entity.*;
import com.janhsu.oday2.service.*;
import com.janhsu.oday2.utils.SerialUtils;
import com.janhsu.oday2.utils.VulnScanUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class MainController {
    @FXML
    private AnchorPane root;
    @FXML
    private TabPane rootTabPane;
    @FXML
    private TableView<ShowVulnTable> showVulnTableView;
    @FXML
    private TableColumn<?, ?> cmsNameCol;
    @FXML
    private TableColumn<?, ?> vulnNameCol;
    @FXML
    private TableColumn<?, ?> vulnTypeCol;
    @FXML
    private TableColumn<?, ?> timeCol;
    @FXML
    private TableColumn<?, ?> vulnIntroCol;
    @FXML
    private Tab vulnScanTab;
    @FXML
    private TabPane scanTabPane;
    @FXML
    private Tab cmsScanTab;
    @FXML
    private Tab allScanTab;
    @FXML
    private Tab customScanTab;
    @FXML
    private Tab usePyExpTab;
    @FXML
    private TableView<ShowPythonExpTable> showPyExpTableView;
    @FXML
    private TableColumn<?, ?> expNameCol;

    @FXML
    private TableColumn<?, ?> expUsageCol;

    @FXML
    private TableColumn<?, ?> expPathCol;

    @FXML
    private TableColumn<?, ?> expUploadTimeCol;

    @FXML
    private TableColumn<?, ?> expAuthorCol;
    @FXML
    private ComboBox<VulnScanInfo> vulnScanCombox;
    @FXML
    private ComboBox<String> cmsScanCombox;
    @FXML
    private TextField vulnNameInput;
    @FXML
    private Label tipsVuln;
    @FXML
    private Label tipsUrl;
    @FXML
    private TextArea vulnScanUrlTextField;
    @FXML
    private TextArea cmsScanUrlTextField;
    @FXML
    private TextArea allScanUrlTextField;
    @FXML
    private TextArea vulnScanResTextArea;
    @FXML
    private TextArea allScanResTextArea;
    @FXML
    private TextArea cmsScanResTextArea;
    @FXML
    private TextField threadNumText;
    @FXML
    private TextField threadNumTextAll;
    @FXML
    private TextField threadNumTextCms;

    @FXML
    private TextField pyNameInput;
    @FXML
    private TextField vulnFilterTextField;

    @FXML
    private Text urlFilePath;
    @FXML
    private TextArea ipPortTextField;
    @FXML
    private TextArea webFindResultTextArea;
    @FXML
    private TextField threadsTextFieldFind;
    @FXML
    private Text filePathTextFind;
    @FXML
    private Text customUrlFileText;
    @FXML
    private Text pocNumText;

    @FXML
    private ListView<VulnScanInfo> allPocListView;
    @FXML
    private ListView<VulnScanInfo> selectedPocListView;
    @FXML
    private TextField customFilterTextField;
    @FXML
    private TextArea customScanUrlTextField;
    @FXML
    private TextArea customScanResTextArea;
    @FXML
    private TextField threadNumTextCustom;

    GetVulnTableService getVulnTableService =  new GetVulnTableService();
    SearchVulnService searchVulnService =  new SearchVulnService();
    GetVulnInfoService getVulnInfoService = new GetVulnInfoService();


    VulnScanUtils vulnScanUtils = new VulnScanUtils();

    String updateResText = "";

    ObservableList<VulnScanInfo> originList;

    String pocNumStr="当前POC数量:%s";

    /**
     * 初始化加载函数
     */
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {

            /**
         * 获取表格数据并展示
         */
        ObservableList<ShowVulnTable> vulnInfoList = getVulnTableService.getShowList();
        vulnNameCol.setCellValueFactory(new PropertyValueFactory("vulnNameString"));
        vulnTypeCol.setCellValueFactory(new PropertyValueFactory("vulnTypeString"));
        timeCol.setCellValueFactory(new PropertyValueFactory("vulnTimeString"));
        vulnIntroCol.setCellValueFactory(new PropertyValueFactory("vulnIntroString"));
        cmsNameCol.setCellValueFactory(new PropertyValueFactory("cmsNameString"));
        showVulnTableView.setItems(vulnInfoList);
        pocNumText.setText(String.format(pocNumStr, vulnInfoList.size()));
        /**
         * 增加scanTabPane的监听事件
         */
        //点击tab时获取扫描漏洞列表
        scanTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab == vulnScanTab) {//点击了漏洞扫描的tab事件
                    try {
                        vulnFilterTextField.clear();
                        List<VulnScanInfo> vulnScanInfos = getVulnInfoService.getVulnScanInfo();
                        ObservableList<VulnScanInfo>  obserList = FXCollections.observableList(vulnScanInfos);
                        vulnScanCombox.getSelectionModel().clearSelection();
                        vulnScanCombox.setItems(obserList);
                        originList = vulnScanCombox.getItems();
                        vulnScanCombox.setConverter(new StringConverter<VulnScanInfo>() {
                            @Override
                            public String toString(VulnScanInfo object) {
                                if (object == null) {
                                    return "";
                                }
                                return object.getVulnName();
                            }

                            @Override
                            public VulnScanInfo fromString(String string) {
                                return null;
                            }

                        });
                    }  catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    /**
                     * 增加下拉栏的监听事件，实现搜索功能
                     */
                    vulnFilterTextField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (newValue == null||newValue.length()==0){
                                vulnScanCombox.setItems(originList);
                            }
                            FilteredList<VulnScanInfo> newlist =originList.filtered(new Predicate<VulnScanInfo>() {
                                @Override
                                public boolean test(VulnScanInfo vulnScanInfo) {
                                    return vulnScanInfo.getVulnName().contains(newValue);
                                }
                            });
                            if (newlist==null){
                                vulnScanCombox.setItems(null);
                            }
                            else {
                                vulnScanCombox.setItems(newlist);
                                vulnScanCombox.hide();
                                vulnScanCombox.show();
                            }

                        }
                    });
                }
/**
 * 点击了CMS扫描的tab事件
 */
                if (newTab == cmsScanTab){//点击了CMS扫描的tab事件
                    try {
                        List cmsList = getVulnInfoService.getCmsInfoList();
                        cmsScanCombox.getItems().clear();
                        cmsScanCombox.getItems().addAll(cmsList);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
/**
 * 点击了自定义扫描的tab事件
 */
                if (newTab == customScanTab){
                    ObservableList customFilterScanAllList;
                    /**
                     * 设置allPocListView的cell
                     */
                    Callback<ListView<VulnScanInfo>, ListCell<VulnScanInfo>> call = TextFieldListCell.forListView(new StringConverter<VulnScanInfo>() {

                        @Override
                        public String toString(VulnScanInfo object) {
                            // TODO Auto-generated method stub
                            return object.getVulnName();
                        }

                        @Override
                        public VulnScanInfo fromString(String string) {
                            return null;
                        }
                    });
                    allPocListView.setCellFactory(call);
                    selectedPocListView.setCellFactory(call);
                    /**
                     * 获取allPocListView的数据源
                     */
                    try {
                        List customScanAllList = getVulnInfoService.getVulnScanInfo();
                        ObservableList<VulnScanInfo>  obserAllList = FXCollections.observableList(customScanAllList);
                        allPocListView.setItems(obserAllList);
                        customFilterScanAllList = allPocListView.getItems();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    /**
                     * 增加allPocListView的监听事件，实现搜索功能
                     */
                    customFilterTextField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (newValue == null||newValue.length()==0){
                                allPocListView.setItems(customFilterScanAllList);
                            }
                            FilteredList<VulnScanInfo> newlist = customFilterScanAllList.filtered(new Predicate<VulnScanInfo>() {
                                @Override
                                public boolean test(VulnScanInfo vulnScanInfo) {
                                    return vulnScanInfo.getVulnName().contains(newValue);
                                }
                            });
                            if (newlist==null){
                                vulnScanCombox.setItems(null);
                            }
                            else {
                                allPocListView.setItems(newlist);
                            }

                        }
                    });


                    /**
                     * 双击allPocListView选择POC
                     */

                    allPocListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getButton().equals(MouseButton.PRIMARY)) {
                                if (event.getClickCount() == 2) {
                                    // 双击事件处理代码
                                    selectedPocListView.getItems().add(allPocListView.getSelectionModel().getSelectedItem());
                                }
                            }
                        }
                    });
                    /**
                     * 双击selectedPocListView删除POC
                     */
                    selectedPocListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getButton().equals(MouseButton.PRIMARY)) {
                                if (event.getClickCount() == 2) {
                                    // 双击事件处理代码
                                    selectedPocListView.getItems().remove(selectedPocListView.getSelectionModel().getSelectedItem());
                                }
                            }
                        }
                    });
                }
            }
        });
        /**
         * 增加rootTabPane的监听事件
         */
        rootTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

                        /**
                         * 打开python脚本调用TAB并获取py脚本表格数据并展示
                         */
                        if (newTab == usePyExpTab){
                            ObservableList<ShowPythonExpTable> pythonInfoList = null;
                            try {
                                pythonInfoList = new GetPythonExpTableService().getShowList();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            expNameCol.setCellValueFactory(new PropertyValueFactory("expNameString"));
                            expUsageCol.setCellValueFactory(new PropertyValueFactory("expUsageString"));
                            expPathCol.setCellValueFactory(new PropertyValueFactory("expPathString"));
                            expUploadTimeCol.setCellValueFactory(new PropertyValueFactory("expTimeString"));
                            expAuthorCol.setCellValueFactory(new PropertyValueFactory("expAuthorString"));
                            showPyExpTableView.setItems(pythonInfoList);
                        }

                    }
                }

        );
        /**
         * 双击脚本进入脚本使用界面监听事件
         */
        showPyExpTableView.setRowFactory( tv -> {
            TableRow<ShowPythonExpTable> pyRow = new TableRow<ShowPythonExpTable>();
            pyRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! pyRow.isEmpty()) ) {
                    ShowPythonExpTable expInfo = pyRow.getItem();
                    try {
                        runPythonExpGui(expInfo.getExpName(),expInfo);//调用方法，向controller中传入参数
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return pyRow;
        });



    }



    /**
     * 打开漏洞添加GUI
     */
    @FXML
    void addVulnGui(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderAddVulnGui = new FXMLLoader(StartApplication.class.getResource("addVuln-view.fxml"));//导入fxml文件
        Scene addVulnGuiScene = new Scene(fxmlLoaderAddVulnGui.load() , 900, 550);//绑定fxml并设置内容大小
        Stage addPocGuiStage = new Stage();//实例化窗体
        addPocGuiStage.setScene(addVulnGuiScene);//设置窗体内容
        addPocGuiStage.setTitle("增加POC");
        addPocGuiStage.getIcons().add(new Image("img/addIcon.png"));
        addPocGuiStage.setResizable(false);
        addPocGuiStage.show();

    }

    /**
     * 搜索漏洞
     */
    @FXML
    void searchVuln(ActionEvent event) throws SQLException, ClassNotFoundException {
                ObservableList<ShowVulnTable> resVulnInfoList = searchVulnService.searchByVulnName(vulnNameInput.getText());
                showVulnTableView.getItems().clear();
                showVulnTableView.setItems(resVulnInfoList);
                pocNumText.setText(String.format(pocNumStr, resVulnInfoList.size()));
    }
    /**
     * 编辑漏洞并打开漏洞添加GUI
     */
    @FXML
    void editVuln(ActionEvent event) throws IOException {
        AddVulnController addVulnController = new AddVulnController();
        addVulnController.setEditUuid(showVulnTableView.getSelectionModel().getSelectedItem().getUuid());

        FXMLLoader fxmlLoaderAddVulnGui = new FXMLLoader(StartApplication.class.getResource("addVuln-view.fxml"));//导入fxml文件
        fxmlLoaderAddVulnGui.setControllerFactory(param -> addVulnController);
        Scene addVulnGuiScene = new Scene(fxmlLoaderAddVulnGui.load() , 900, 550);//绑定fxml并设置内容大小
        Stage addPocGuiStage = new Stage();//实例化窗体
        addPocGuiStage.setScene(addVulnGuiScene);//设置窗体内容
        addPocGuiStage.setTitle("编辑POC");
        addPocGuiStage.getIcons().add(new Image("img/addIcon.png"));
        addPocGuiStage.setResizable(false);
        addPocGuiStage.show();
    }



    /**
     * 单个漏洞批量扫描
     */
    @FXML
    void clickVulnScan(ActionEvent event) throws IOException {
        if (this.vulnScanCombox.getSelectionModel().selectedItemProperty().getValue()==null){//判断选择漏洞是否为空
            this.tipsVuln.setVisible(true);
        }
        else if (this.vulnScanUrlTextField.getText()==null||this.vulnScanUrlTextField.getText().trim().length()==0){//判断输入URl是否为空
            this.tipsUrl.setVisible(true);
        }
        else {
            new VulnScanController().vulnScan(
                    this.vulnScanCombox.getSelectionModel().selectedItemProperty().getValue(),
                    vulnScanUtils.textToList(this.vulnScanUrlTextField.getText()),
                    Integer.valueOf(this.threadNumText.getText()),
                    (Stage) this.root.getScene().getWindow(),
                    vulnScanResTextArea
            );
        }
    }

    /**
     * 全部漏洞批量扫描
     */
    @FXML
    void clickAllScan(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        new AllVulnScanController().allVulnScan(
        getVulnInfoService.getVulnScanInfo(),
        new VulnScanUtils().textToList(allScanUrlTextField.getText()),
        Integer.valueOf(this.threadNumTextAll.getText()),
        (Stage) this.root.getScene().getWindow(),
        allScanResTextArea, "全部漏洞扫描"
);
    }

    /**
     * 按CMS扫描
     */
    @FXML
    void clickCmsScan(ActionEvent event) throws SQLException,  IOException {
        new AllVulnScanController().allVulnScan(
                getVulnInfoService.getVulnScanInfoByCmsName(this.cmsScanCombox.getSelectionModel().getSelectedItem().toString()),
                vulnScanUtils.textToList(cmsScanUrlTextField.getText()),
                Integer.valueOf(this.threadNumTextCms.getText()),
                (Stage) this.root.getScene().getWindow(),
                cmsScanResTextArea,
                "CMS扫描"
        );
    }

    /**
     * 自定义扫描
     */
    @FXML
    void clickCustomScan(ActionEvent event) throws IOException {
        new AllVulnScanController().allVulnScan(this.selectedPocListView.getItems(),
                vulnScanUtils.textToList(customScanUrlTextField.getText()),
                Integer.valueOf(this.threadNumTextCustom.getText()),
                (Stage) this.root.getScene().getWindow(),
                customScanResTextArea,
                "自定义扫描"
        );
    }


    /**
     * 打开设置代理界面
     */
    @FXML
    void setProxy(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderSetting = new FXMLLoader(StartApplication.class.getResource("setProxy-view.fxml"));//导入fxml文件
        Scene settingGuiScene = new Scene(fxmlLoaderSetting.load() , 400, 230);//绑定fxml并设置内容大小
        Stage settingGuiStage = new Stage();//实例化窗体
        settingGuiStage.setScene(settingGuiScene);//设置窗体内容
        settingGuiStage.setTitle("设置");
        settingGuiStage.getIcons().add(new Image("img/settings.png"));
        settingGuiStage.setResizable(false);
        settingGuiStage.show();

    }
    /**
     * 打开导入python界面
     */
    @FXML
    void uploadPyExp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderUploadPy = new FXMLLoader(StartApplication.class.getResource("uploadPythonExp-view.fxml"));//导入fxml文件
        Scene UploadPyGuiScene = new Scene(fxmlLoaderUploadPy.load() , 700, 500);//绑定fxml并设置内容大小
        Stage UploadPyGuiStage = new Stage();//实例化窗体
        UploadPyGuiStage.setScene(UploadPyGuiScene);//设置窗体内容
        UploadPyGuiStage.setTitle("导入");
        UploadPyGuiStage.getIcons().add(new Image("img/import.png"));
        UploadPyGuiStage.setResizable(false);
        UploadPyGuiStage.show();
    }
    /**
     * 打开编辑python脚本界面
     */
    @FXML
    void updatePythonExp(ActionEvent event) throws IOException {
        UploadPythonExpController uploadPythonExpController = new UploadPythonExpController();
        uploadPythonExpController.setEditUuid(showPyExpTableView.getSelectionModel().getSelectedItem().getUuid());
        FXMLLoader fxmlLoaderUploadPy = new FXMLLoader(StartApplication.class.getResource("uploadPythonExp-view.fxml"));//导入fxml文件
        fxmlLoaderUploadPy.setControllerFactory(param -> uploadPythonExpController);
        Scene UploadPyGuiScene = new Scene(fxmlLoaderUploadPy.load() , 700, 500);//绑定fxml并设置内容大小
        Stage UploadPyGuiStage = new Stage();//实例化窗体
        UploadPyGuiStage.setScene(UploadPyGuiScene);//设置窗体内容
        UploadPyGuiStage.setTitle("上传");
        UploadPyGuiStage.getIcons().add(new Image("img/upload.png"));
        UploadPyGuiStage.setResizable(false);
        UploadPyGuiStage.show();
    }
    /**
     * 打开python设置界面
     */
    @FXML
    void pythonSetting(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderPySetting = new FXMLLoader(StartApplication.class.getResource("setPythonModule-view.fxml"));//导入fxml文件
        Scene pySettingGuiScene = new Scene(fxmlLoaderPySetting.load() , 400, 230);//绑定fxml并设置内容大小
        Stage pySettingGuiStage = new Stage();//实例化窗体
        pySettingGuiStage.setScene(pySettingGuiScene);//设置窗体内容
        pySettingGuiStage.setTitle("设置");
        pySettingGuiStage.getIcons().add(new Image("img/settings.png"));
        pySettingGuiStage.setResizable(false);
        pySettingGuiStage.show();
    }

    /**
     * 搜索python脚本
     */
    @FXML
    void searchPyExp(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<ShowPythonExpTable> resExpInfoList = new GetPythonExpTableService().searchByExpName(pyNameInput.getText());
        showPyExpTableView.getItems().clear();
        showPyExpTableView.setItems(resExpInfoList);
    }

    /**
     * python脚本利用界面
     */
    private void runPythonExpGui(String name,ShowPythonExpTable showPythonExpTable) throws IOException {
        RunPythonExpController runPythonExpController = new RunPythonExpController();
        runPythonExpController.setShowPythonExpTable(showPythonExpTable);

        FXMLLoader fxmlLoaderrunPythonExpGui = new FXMLLoader(StartApplication.class.getResource("runPythonExp-view.fxml"));//导入fxml文件
        fxmlLoaderrunPythonExpGui.setControllerFactory(param -> runPythonExpController);


        Parent parent = fxmlLoaderrunPythonExpGui.load();
        Scene runPythonExpGuiScene = new Scene(parent , 700, 500);//绑定fxml并设置内容大小


        Stage runPythonExpGuiStage = new Stage();//实例化窗体
        runPythonExpGuiStage.setScene(runPythonExpGuiScene);//设置窗体内容
        runPythonExpGuiStage.setTitle(name+"-脚本调用");
        runPythonExpGuiStage.getIcons().add(new Image("img/run.png"));
        runPythonExpGuiStage.setResizable(false);
        runPythonExpGuiStage.show();
    }

    /**
     * 全部漏洞扫描导入URl
     */
    @FXML
    void importUrlFile(ActionEvent event) {
        File file = fileChoose();
        urlFilePath.setText("已导入:"+String.valueOf(file));
        String urlText = readFile(file);
        allScanUrlTextField.clear();
        allScanUrlTextField.setText(urlText);
    }
    /**
     * 自定义漏洞扫描导入URl
     */
    @FXML
    void importCustomUrlFile(ActionEvent event) {
        File file = fileChoose();
        customUrlFileText.setText("已导入:"+String.valueOf(file));
        String urlText = readFile(file);
        customScanUrlTextField.clear();
        customScanUrlTextField.setText(String.valueOf(urlText));
    }
    /**
     * web-Finder导入URl
     */
    @FXML
    void webFindImport(ActionEvent event) {
        File file = fileChoose();
        filePathTextFind.setText("已导入:"+String.valueOf(file));
        String urlText = readFile(file);
        ipPortTextField.clear();
        ipPortTextField.setText(String.valueOf(urlText));
    }
    /**
     * 封装选择文件方法
     */
    private File fileChoose(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        return file;
    }
    /**
     * 封装读文件方法
     */
    private String readFile(File file){
        StringBuilder urlText = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                urlText.append(line).append("\n");
                // read next line
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(urlText);
    }

    @FXML
    void startWebFinder(ActionEvent event) throws IOException {
        new WebFinderController().start(new VulnScanUtils().textToList(ipPortTextField.getText()),
                Integer.parseInt(threadsTextFieldFind.getText()),
                (Stage) root.getScene().getWindow(),
                webFindResultTextArea);
    }
    @FXML
    void exportRes(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        try
        {
            // 创建文件对象
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(file);
            // 写文件
            fileWriter.write(webFindResultTextArea.getText());
            // 关闭
            fileWriter.close();
        }
        catch (IOException e)
        {
            //
            e.printStackTrace();
        }
    }
    /**
     * 导出漏洞
     */
    @FXML
    void exportVuln(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        Vuln vuln = new ExportVulnService().getExportVuln(showVulnTableView.getSelectionModel().getSelectedItem().getUuid());
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SerialFile", "*.object");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(showVulnTableView.getSelectionModel().getSelectedItem().getVulnName());
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        new SerialUtils().vulnToFile(file,vuln);
    }

    /**
     * 导入漏洞
     */
    @FXML
    void importVulnObject(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        ImportVulnService importVulnService = new ImportVulnService();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SerialFile", "*.object");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        Vuln vulnInfo = new SerialUtils().fileToVuln(file);
        if (importVulnService.ifVulnIdExist(vulnInfo.getUuid())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("\n漏洞uuid已存在!");
            alert.show();
        }
        else {
            importVulnService.insert(vulnInfo);
        }
        searchVuln(new ActionEvent());
    }

    @FXML
    void deleteVuln(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // 创建一个确认对话框
        alert.setTitle("提示");
        alert.setHeaderText(null); // 设置对话框的头部文本
        // 设置对话框的内容文本
        alert.setContentText("\n是否要删除:"+showVulnTableView.getSelectionModel().getSelectedItem().getVulnName());
        // 显示对话框，并等待按钮返回
        Optional<ButtonType> buttonType = alert.showAndWait();
        // 判断返回的按钮类型是确定还是取消，再据此分别进一步处理
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) { // 单击了确定按钮OK_DONE
            new AddVulnService().deleteByUuid(showVulnTableView.getSelectionModel().getSelectedItem().getUuid());
            searchVuln(new ActionEvent());
        } else { // 单击了取消按钮CANCEL_CLOSE
            System.out.println("取消");
        }
    }


}
package student.manager.view;

import student.manager.javebean.*;
import student.manager.service.ClassInfoService;
import student.manager.service.ScoreService;
import student.manager.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class StudentInfoManagementSystem extends JFrame {
    // 单例模式--设计模式 ↓
    private StudentService studentService = StudentService.getInstance();
    private ClassInfoService classInfoService = ClassInfoService.getInstance();
    private ScoreService scoreService = ScoreService.getInstance();
    // 单例模式--设计模式 ↑
    private List<Student> students = new ArrayList<>();
    private List<ClassInfo> classes = new ArrayList<>();

    private JTextField nameField, studentIdField, birthdateField, hometownField;
    private JTextField nameEditField, studentIdEditField, birthdateEditField, hometownEditField;
    private JTextField courseNameField, scoreNameField;
    private JTextField nameSearchField, studentIdSearchField;
    private JTextField classInfoNameField, classInfoMajorField, classInfoStartTimeField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<ClassInfo> classComboBox;
    private JComboBox<ClassInfo> classComboEditBox;
    private JComboBox<ClassInfo> classSearchComboBox;
    private JTable studentTable,scoreTable;
    private DefaultTableModel tableModel,scoreTableModel;
    private JButton scoreSearchButton,deleteScoreButton;
    private JButton addStudentButton, deleteStudentButton, searchButton, editStudentButton, saveEditButton,signScoreButton;
    private JButton addClassInfoButton;
    private JButton lookHomeTownButton, lookAgeButton;

    public StudentInfoManagementSystem() {
        // 加载学生信息
        students = studentService.queryAllStudents();
        // 加载班级信息
        classes = classInfoService.queryAllClassInfo();

        setTitle("Student information management system");
        // 设置窗口结束程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setSize(650, 400);
        setLocationRelativeTo(null);

        // 主窗口
        JTabbedPane tabbedPane = new JTabbedPane();

        // 录入班级信息。
        JPanel classPanel = createClassPanel();
        // 录入学生基本信息。
        JPanel studentPanel = createStudentPanel();

        // 班级录入和学生信息录入在两个JPanel中实现中实现，
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, classPanel, studentPanel);
        splitPane.setDividerLocation(200);
        tabbedPane.addTab("information entry", splitPane);

        // 信息信息面板
        JPanel studentInfoPanel = createStudentInfoPanel();
        tabbedPane.addTab("student information", studentInfoPanel);
        // 成绩信息面板
        JPanel scoreInfoPanel = createScoreInfoPanel();


        tabbedPane.addTab("grade information", scoreInfoPanel);
        add(tabbedPane);


        setVisible(true);


    }


    /**
     * 成绩信息展示面板
     * @return
     */
    private JPanel createScoreInfoPanel() {
        JPanel scorePanel = new JPanel();
        // 上方为搜索面板
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 6, 10, 10));
        JLabel nameLabel = new JLabel("name:");
        scoreNameField = new JTextField();
        JLabel courseNameLabel = new JLabel("course:");
        courseNameField = new JTextField();
        scoreSearchButton = new JButton("search");
        scoreSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = scoreNameField.getText();
                String courseName = courseNameField.getText();
                if (name.isEmpty() && courseName.isEmpty()) {
                    updateScoreTableModel(null);
                    return;
                }
                List<Score> list = scoreService.queryScoresWithParam(name, courseName);
                // 更新tableModel
                updateScoreTableModel(list);
            }
        });
        deleteScoreButton = new JButton("delete grade");
        deleteScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的行
                int selectedRow = scoreTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select the grade you want to delete");
                    return;
                }
                // 获取选中行的号
                Integer id = (Integer) scoreTable.getValueAt(selectedRow, 0);
                scoreService.deleteScoreById(id);
                // 更新表格
                updateScoreTableModel(null);
            }
        });
        searchPanel.add(nameLabel);
        searchPanel.add(scoreNameField);
        searchPanel.add(courseNameLabel);
        searchPanel.add(courseNameField);
        searchPanel.add(scoreSearchButton);
        searchPanel.add(deleteScoreButton);


        // 中间表格展示成绩信息
        // 设置表头
        scoreTableModel = new DefaultTableModel(new String[]{"number", "student number", "name", "course", "grade"}, 0);
        // 设置表格
        scoreTable = new JTable(scoreTableModel);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setPreferredSize(new Dimension(630, 300));
        // 设置表格内容
        updateScoreTableModel(null);
        scorePanel.add(searchPanel, BorderLayout.NORTH);
        scorePanel.add(scrollPane, BorderLayout.CENTER);

        return scorePanel;

    }

    private void updateScoreTableModel(List<Score> list) {
        scoreTableModel.setRowCount(0);
        if (list == null || list.isEmpty()) {
            list = scoreService.queryAllScores();
        }
        for (Score score : list) {
            scoreTableModel.addRow(new Object[]{score.getId(),score.getStudentId(), score.getStudentName(), score.getCourseName(), score.getGrade()});
        }
    }


    /**
     * 学生信息展示面板
     * @return JPanel
     */
    private JPanel createStudentInfoPanel() {
        JPanel jPanel = new JPanel();
        // 上方为搜索面板
        // 可按单一条件模式或高级查询学生基本信息(7分)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 7, 10, 10));
        classSearchComboBox = new JComboBox<>();
        classSearchComboBox.addItem(new ClassInfo());
        for (ClassInfo aClass : classes) {
            classSearchComboBox.addItem(aClass);
        }
        JLabel nameLabel = new JLabel("name:");
        nameSearchField = new JTextField();
        JLabel studentIdLabel = new JLabel("student number:");
        studentIdSearchField = new JTextField();
        searchButton = new JButton("search");


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameSearchField.getText();
                String id = studentIdSearchField.getText();
                ClassInfo classInfo = (ClassInfo) classSearchComboBox.getSelectedItem();
                if (name.isEmpty() && id.isEmpty() && classInfo.getClassId() == null) {
                    updateTableModel();
                    return;
                }
                List<StudentVo> list = studentService.queryStudentsWithParam(name, id, classInfo.getClassId());
                // 更新tableModel
                updateTableModelWithList(list);

            }
        });
        searchPanel.add(new JLabel("class:"));
        searchPanel.add(classSearchComboBox);
        searchPanel.add(nameLabel);
        searchPanel.add(nameSearchField);
        searchPanel.add(studentIdLabel);
        searchPanel.add(studentIdSearchField);
        searchPanel.add(searchButton);
//        searchPanel.add(deleteStudentButton);
//        searchPanel.add(editStudentButton);
        // 中间为为学生信息展示面板

        // 设置表头
        tableModel = new DefaultTableModel(new String[]{"studentID", "name", "class", "data of birth", "native place", "gender"}, 0);
        // 设置表格
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(630, 250));
        // 设置表格内容
        updateTableModel();
        jPanel.add(searchPanel, BorderLayout.NORTH);
        jPanel.add(scrollPane, BorderLayout.CENTER);
        // 下方为统计面板
        signScoreButton = new JButton("record scores");
        signScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的行
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select the student whose scores you want to record");
                    return;
                }
                // 获取选中行的学号
                String id = (String) studentTable.getValueAt(selectedRow, 0);
                createSignScoreJFrame(id);
            }
        });
        deleteStudentButton = new JButton("delete student information");
        // 删除学生按钮的监听器
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的行
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select the student you want to delete");
                    return;
                }
                // 获取选中行的学号
                String id = (String) studentTable.getValueAt(selectedRow, 0);
                // 查询学生是否有成绩
                List<Score> scores = scoreService.queryScoresWithStudentId(id);
                if (!scores.isEmpty()) {
                    // 删除学生对应的成绩
                    scoreService.deleteScoreByStudentId(id);
                }
                // 数据库删除学生
                studentService.deleteStudentById(id);
                // 更新students
                students = studentService.queryAllStudents();
                // 更新表格
                updateTableModel();
            }
        });
        editStudentButton = new JButton("modify student information");
        // 修改学生按钮的监听器
        editStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出新界面进行修改
                // 获取选中的行
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select the student you want to modify");
                    return;
                }
                // 获取选中行的学号
                String id = (String) studentTable.getValueAt(selectedRow, 0);
                createEditStudentJFrame(id);
            }
        });
        lookAgeButton = new JButton("view age distribution");
        lookHomeTownButton = new JButton("view home distribution");
        // 查看年龄分布按钮的监听器
        lookAgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出新窗口显示年龄分布
                JFrame jFrame = new JFrame("age distribution");
                jFrame.setSize(400, 300);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(new BorderLayout());
                // 计算年龄分布
                Map<String, Integer> ageMap = new HashMap<>();
                int total = 0;
                for (Student student : students) {
                    // 获取当前年份
                    String year = new SimpleDateFormat("yyyy").format(new Date());
//                    student.getBirthday()截取前四位年份
                    String birthday = student.getBirthday();
                    // 截取前四位
                    birthday = birthday.substring(0, 4);
                    int age = Integer.parseInt(year) - Integer.parseInt(birthday);
                    total++;
                    if (ageMap.containsKey(String.valueOf(age))) {
                        ageMap.put(String.valueOf(age), ageMap.get(String.valueOf(age)) + 1);
                    } else {
                        ageMap.put(String.valueOf(age), 1);
                    }
                }
                // 绘制柱状图
                BarPanel barPanel = new BarPanel(ageMap, total);
                jFrame.add(barPanel, BorderLayout.CENTER);
                jFrame.setVisible(true);
            }
        });
        // 查看籍贯分布按钮的监听器
        lookHomeTownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出新窗口显示籍贯分布
                JFrame jFrame = new JFrame("distribution of origin");
                jFrame.setSize(400, 300);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(new BorderLayout());
                // 计算籍贯分布
                Map<String, Integer> homeTownMap = new HashMap<>();
                int total = 0;
                for (Student student : students) {
                    total++;
                    if (homeTownMap.containsKey(student.getHometown())) {
                        homeTownMap.put(student.getHometown(), homeTownMap.get(student.getHometown()) + 1);
                    } else {
                        homeTownMap.put(student.getHometown(), 1);
                    }
                }
                // 绘制柱状图
                BarPanel barPanel = new BarPanel(homeTownMap, total);
                jFrame.add(barPanel, BorderLayout.CENTER);
                jFrame.setVisible(true);
            }
        });
        JPanel buttonBottom = new JPanel();
        buttonBottom.setLayout(new GridLayout(1, 2, 10, 10));
        buttonBottom.add(lookAgeButton);
        buttonBottom.add(lookHomeTownButton);
        buttonBottom.add(deleteStudentButton);
        buttonBottom.add(editStudentButton);
        buttonBottom.add(signScoreButton);
        jPanel.add(buttonBottom, BorderLayout.SOUTH);
        return jPanel;
    }

    private void createSignScoreJFrame(String id) {
        // 弹出新界面录入成绩  （课程名称、成绩）
        JFrame jFrame = new JFrame("record scores");
        jFrame.setSize(400, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel studentIdLabel = new JLabel("studentID:");
        JTextField studentIdField = new JTextField(id);
        studentIdField.setEditable(false);
        JLabel courseNameLabel = new JLabel("course title:");
        JTextField courseNameField = new JTextField();
        JLabel gradeLabel = new JLabel("grade:");
        JTextField gradeField = new JTextField();
        // gradeField只能输入数字
        gradeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });
        JButton saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String grade = gradeField.getText();
                if (courseName.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the complete information");
                    return;
                }
                // 数据库添加成绩
                scoreService.addScore(id, courseName, Integer.parseInt(grade));
                JOptionPane.showMessageDialog(null, "record successfully");
                updateScoreTableModel(null);
                jFrame.dispose();
            }
        });
        jFrame.add(studentIdLabel);
        jFrame.add(studentIdField);
        jFrame.add(courseNameLabel);
        jFrame.add(courseNameField);
        jFrame.add(gradeLabel);
        jFrame.add(gradeField);
        jFrame.add(new JLabel());
        jFrame.add(saveButton);
        jFrame.setVisible(true);
    }

    private void updateTableModelWithList(List<StudentVo> list) {
        tableModel.setRowCount(0);
        for (StudentVo studentVo : list) {
            tableModel.addRow(new Object[]{studentVo.getId(), studentVo.getName(), studentVo.getClassName(),
                    studentVo.getBirthday(), studentVo.getHometown(), studentVo.getSex()});
        }
    }

    /**
     * 修改学生信息面板
     * @param id 学号
     */
    private void createEditStudentJFrame(String id) {
        // 数据库查询学生信息
        Student student = studentService.queryStudentById(id);
        // 弹出新界面进行修改 学号不能修改
        JFrame jFrame = new JFrame("modify student information");
        jFrame.setSize(400, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridLayout(7, 2, 10, 10));
        JLabel studentIdLabel = new JLabel("studentId:");
        studentIdEditField = new JTextField(student.getId());
        studentIdEditField.setEditable(false);
        JLabel nameLabel = new JLabel("name:");
        nameEditField = new JTextField(student.getName());
        JLabel birthdateLabel = new JLabel("data of birth:");
        birthdateEditField = new JTextField(student.getBirthday());
        // birthdateEditField只能输入数字
        birthdateEditField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });
        JLabel hometownLabel = new JLabel("native place:");
        hometownEditField = new JTextField(student.getHometown());
        JLabel classLabel = new JLabel("the class:");
        classComboEditBox = new JComboBox<>();
        for (ClassInfo aClass : classes) {

            if (aClass.getClassId().equals(student.getClassId())) {
                classComboEditBox.addItem(aClass);
            }
        }
        for (ClassInfo aClass : classes) {
            if (!aClass.getClassId().equals(student.getClassId())) {
                classComboEditBox.addItem(aClass);
            }
        }
        ButtonGroup genderGroup = new ButtonGroup();
        JRadioButton maleRadio = new JRadioButton("male");
        maleRadio.setActionCommand("male");
        JRadioButton femaleRadio = new JRadioButton("female");
        femaleRadio.setActionCommand("female");
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        if (student.getSex().equals("male")) {
            maleRadio.setSelected(true);
        } else {
            femaleRadio.setSelected(true);
        }

        jFrame.add(studentIdLabel);
        jFrame.add(studentIdEditField);
        jFrame.add(nameLabel);
        jFrame.add(nameEditField);
        jFrame.add(birthdateLabel);
        jFrame.add(birthdateEditField);
        jFrame.add(hometownLabel);
        jFrame.add(hometownEditField);
        jFrame.add(classLabel);
        jFrame.add(classComboEditBox);
        jFrame.add(maleRadio);
        jFrame.add(femaleRadio);
        saveEditButton = new JButton("save");
        saveEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameEditField.getText();
                String birthday = birthdateEditField.getText();
                String hometown = hometownEditField.getText();
                ClassInfo classInfo = (ClassInfo) classComboEditBox.getSelectedItem();
                String sex = genderGroup.getSelection().getActionCommand();
                if (name.isEmpty() || birthday.isEmpty() || hometown.isEmpty() || classInfo.getClassId() == null || sex.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the complete information");
                    return;
                }
                // 更新数据库对应id的学生信息
                studentService.updateStudent(id,name,birthday,hometown,sex,classInfo.getClassId());
                // 更新表格
                updateTableModel();
                JOptionPane.showMessageDialog(null, "modify successfully");
                jFrame.dispose();

            }
        });
        jFrame.add(new Label());
        jFrame.add(saveEditButton);


        jFrame.setVisible(true);
    }

    /**
     * 添加课程信息面板
     * @return JPanel
     */
    private JPanel createClassPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        JLabel nameLabel = new JLabel("class name:");
        classInfoNameField = new JTextField();
        JLabel majorLabel = new JLabel("major:");
        classInfoMajorField = new JTextField();
        JLabel startTimeLabel = new JLabel("class start time:");
        classInfoStartTimeField = new JTextField();
        // classInfoStartTimeField只能输入数字
        classInfoStartTimeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });

        addClassInfoButton = new JButton("Add class");
        // 添加班级按钮的监听器
        addClassInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = classInfoNameField.getText();
                String major = classInfoMajorField.getText();
                String startTime = classInfoStartTimeField.getText();
                if (name.isEmpty() || major.isEmpty() || startTime.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the complete information");
                    return;
                }
                ClassInfo classInfo = new ClassInfo( name, major, startTime);
                // 数据库添加班级
                classInfoService.addClassInfo(classInfo);
                // 更新classes
                classes = classInfoService.queryAllClassInfo();
                // 更新classComboBox
                updateClassComboBox();

                JOptionPane.showMessageDialog(null, "class added successfully");
                // 清空输入框
                classInfoNameField.setText("");
                classInfoMajorField.setText("");
                classInfoStartTimeField.setText("");
            }
        });
        panel.add(nameLabel);
        panel.add(classInfoNameField);
        panel.add(majorLabel);
        panel.add(classInfoMajorField);
        panel.add(startTimeLabel);
        panel.add(classInfoStartTimeField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(addClassInfoButton);


        return panel;
    }

    // 更新下拉框
    private void updateClassComboBox() {
        classComboBox.removeAllItems();
        classSearchComboBox.removeAllItems();
        classComboBox.addItem(new ClassInfo());
        classSearchComboBox.addItem(new ClassInfo());
        for (ClassInfo aClass : classes) {
            classComboBox.addItem(aClass);
            classSearchComboBox.addItem(aClass);
        }
    }

    /**
     * 添加学生信息面板
     * @return JPanel
     */
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("name:");
        nameField = new JTextField();
        JLabel studentIdLabel = new JLabel("studentID:");

        studentIdField = new JTextField();
        // studentIdField只能输入数字
        studentIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });
        JLabel birthdateLabel = new JLabel("data of birth:");
        birthdateField = new JTextField();
        // birthdateField只能输入数字
        birthdateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });
        JLabel hometownLabel = new JLabel("native place:");
        hometownField = new JTextField();

        JLabel classLabel = new JLabel("The class:");
        classComboBox = new JComboBox<>();
        classComboBox.addItem(new ClassInfo());
        for (ClassInfo aClass : classes) {
            classComboBox.addItem(aClass);
        }
        ButtonGroup genderGroup = new ButtonGroup();
        maleRadio = new JRadioButton("male");
        maleRadio.setActionCommand("male");
        femaleRadio = new JRadioButton("female");
        femaleRadio.setActionCommand("female");
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        addStudentButton = new JButton("add student");
        // 添加学生按钮的监听器
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String id = studentIdField.getText();
                String birthday = birthdateField.getText();
                String hometown = hometownField.getText();
                ClassInfo classInfo = (ClassInfo) classComboBox.getSelectedItem();
                String sex = null;
                try {
                    sex = genderGroup.getSelection().getActionCommand();
                } catch (Exception ex) {
                    // do nothing
                }
                if (name.isEmpty() || id.isEmpty() || birthday.isEmpty() || hometown.isEmpty() || classInfo.getClassId() == null || sex == null) {
                    JOptionPane.showMessageDialog(null, "Please fill in the complete information");
                    return;
                }
                // 判断学号是否重复
                Student student = studentService.queryStudentById(id);
                if (student != null) {
                    JOptionPane.showMessageDialog(null, "repeat student number");
                    return;
                }
                student = new Student(name, id, sex, hometown, birthday, classInfo.getClassId());
                // 数据库添加学生
                studentService.addStudent(student);
                students = studentService.queryAllStudents();
                // 更新表格
                updateTableModel();

                // 弹框提示添加成功
                JOptionPane.showMessageDialog(null, "successfully added");
                // 清空输入框
                nameField.setText("");
                studentIdField.setText("");
                birthdateField.setText("");
                hometownField.setText("");
                classComboBox.setSelectedIndex(0);
                genderGroup.clearSelection();


            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(studentIdLabel);
        panel.add(studentIdField);
        panel.add(birthdateLabel);
        panel.add(birthdateField);
        panel.add(hometownLabel);
        panel.add(hometownField);
        panel.add(classLabel);
        panel.add(classComboBox);
        panel.add(maleRadio);
        panel.add(femaleRadio);
        panel.add(new JLabel());
        panel.add(addStudentButton);

        return panel;
    }

    private void updateTableModel() {
        tableModel.setRowCount(0);

        List<StudentVo> studentVos = studentService.queryAllStudentVos();
        for (StudentVo studentVo : studentVos) {
            tableModel.addRow(new Object[]{studentVo.getId(), studentVo.getName(), studentVo.getClassName(),
                    studentVo.getBirthday(), studentVo.getHometown(), studentVo.getSex()});
        }
    }




}



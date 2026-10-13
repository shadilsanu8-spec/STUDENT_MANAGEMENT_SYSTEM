import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

// Student class
class Student {
    int id;
    String name, course;
    double marks;

    Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    String grade() {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }
}

// Main GUI class
public class StudentManagementSystem extends JFrame {

    private final Color navy = new Color(25, 42, 76);
    private final Color blue = new Color(50, 112, 235);

    private final ArrayList<Student> students = new ArrayList<>();
    // Keeps deleted records so they can be restored during this session.
    private final ArrayList<Student> deletedStudents = new ArrayList<>();

    private final JTextField id = new JTextField();
    private final JTextField name = new JTextField();
    private final JTextField course = new JTextField();
    private final JTextField marks = new JTextField();
    private final JTextField search = new JTextField(18);

    private final DefaultTableModel model =
            new DefaultTableModel(
                    new String[]{"ID", "Student Name", "Course", "Marks", "Grade"}, 0) {
                @Override
                public boolean isCellEditable(int r, int c) {
                    return false;
                }
            };

    private final JTable table = new JTable(model);
    private final TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(model);

    private final JLabel status =
            new JLabel("Ready - add a student to get started.");

    private final JLabel count = new JLabel("Total Students: 0");

    public StudentManagementSystem() {
        super("Student Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(244, 247, 252));
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(
                BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JLabel title =
                new JLabel("STUDENT MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
                "Manage student records | Search | Update | Grades");
        subtitle.setForeground(new Color(210, 222, 242));

        JPanel titles = new JPanel(new GridLayout(2, 1, 0, 5));
        titles.setOpaque(false);
        titles.add(title);
        titles.add(subtitle);

        JLabel badge = new JLabel(" JAVA | SWING ");
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(new Color(48, 67, 105));

        header.add(titles, BorderLayout.CENTER);
        header.add(badge, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        content.setBorder(
                BorderFactory.createEmptyBorder(20, 22, 16, 22));

        // Student form card
        JPanel formCard = card();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.add(
                section("Student Details",
                        "Enter student information below."),
                BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridLayout(2, 2, 12, 10));
        fields.setOpaque(false);
        fields.add(fieldPanel("Student ID", id));
        fields.add(fieldPanel("Full Name", name));
        fields.add(fieldPanel("Course", course));
        fields.add(fieldPanel("Marks (0-100)", marks));
        formCard.add(fields, BorderLayout.CENTER);

        JPanel buttons =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        buttons.setOpaque(false);

        JButton add = button("Add Student", blue, Color.WHITE);
        JButton update = button(
                "Update Selected", new Color(15, 148, 105), Color.WHITE);
        JButton delete = button(
                "Delete Selected", new Color(218, 65, 80), Color.WHITE);
        JButton clear = button(
                "Clear Fields", new Color(232, 237, 245), navy);
        JButton restore = button(
                "Restore Deleted", new Color(126, 87, 194), Color.WHITE);

        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);
        buttons.add(clear);
        buttons.add(restore);
        formCard.add(buttons, BorderLayout.SOUTH);

        // Records card
        JPanel recordsCard = card();
        recordsCard.setLayout(new BorderLayout(0, 12));

        JPanel top = new JPanel(new BorderLayout(10, 5));
        top.setOpaque(false);
        top.add(
                section("Student Records",
                        "Select a row to update or delete."),
                BorderLayout.WEST);

        JPanel searchBox =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        searchBox.setOpaque(false);
        searchBox.add(new JLabel("Search:"));
        styleField(search);
        searchBox.add(search);

        JButton reset = button(
                "Reset", new Color(232, 237, 245), navy);
        searchBox.add(reset);
        top.add(searchBox, BorderLayout.EAST);
        recordsCard.add(top, BorderLayout.NORTH);

        table.setRowSorter(sorter);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setGridColor(new Color(232, 237, 244));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(219, 233, 255));
        table.setSelectionForeground(navy);
        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(
                new Color(238, 243, 251));

        JScrollPane scroll = new JScrollPane(table);
        recordsCard.add(scroll, BorderLayout.CENTER);

        count.setFont(new Font("SansSerif", Font.BOLD, 12));
        count.setForeground(navy);
        recordsCard.add(count, BorderLayout.SOUTH);

        JPanel stack = new JPanel(new BorderLayout(0, 16));
        stack.setOpaque(false);
        stack.add(formCard, BorderLayout.NORTH);
        stack.add(recordsCard, BorderLayout.CENTER);
        content.add(stack, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(235, 240, 248));
        footer.setBorder(
                BorderFactory.createEmptyBorder(9, 24, 9, 24));
        footer.add(status, BorderLayout.WEST);
        footer.add(
                new JLabel("Memory Storage | Restore Deleted Available"),
                BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        // Button actions
        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clearFields());
        restore.addActionListener(e -> restoreDeletedStudent());
        reset.addActionListener(e -> search.setText(""));

        // Live search
        search.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                });

        // Load selected record into form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelected();
            }
        });

        // Load 100 sample student records when the program starts.
        loadSampleStudents();
    }

    private void loadSampleStudents() {
        String[] firstNames = {
                "Aarav", "Aanya", "Aditya", "Ananya", "Arjun",
                "Diya", "Ishaan", "Meera", "Rahul", "Sneha"
        };
        String[] lastNames = {
                "Nair", "Menon", "Sharma", "Patel", "Khan",
                "Das", "Thomas", "Joseph", "Reddy", "Pillai"
        };
        String[] courses = {
                "BCA", "BSc Computer Science", "BCom", "BA English",
                "BBA", "BSc Mathematics", "BSc Physics", "BSW",
                "BSc Chemistry", "BA Economics"
        };

        for (int i = 0; i < 100; i++) {
            int studentId = 1001 + i;
            String studentName =
                    firstNames[i % firstNames.length] + " " +
                            lastNames[(i / firstNames.length) % lastNames.length];
            String studentCourse = courses[i % courses.length];
            double studentMarks = 45 + ((i * 7) % 56);

            students.add(new Student(
                    studentId, studentName, studentCourse, studentMarks));
        }

        refresh();
        status.setText("100 sample student records loaded.");
    }

    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(229, 234, 243)),
                BorderFactory.createEmptyBorder(16, 17, 16, 17)));
        return p;
    }

    private JPanel section(String titleText, String subtitleText) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 4));
        p.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(navy);

        JLabel subtitle = new JLabel(subtitleText);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setForeground(new Color(100, 116, 139));

        p.add(title);
        p.add(subtitle);
        return p;
    }

    private JPanel fieldPanel(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));

        styleField(field);
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(211, 220, 233)),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        f.setPreferredSize(new Dimension(170, 36));
    }

    private JButton button(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setForeground(fg);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 13, 10, 13));
        return b;
    }

    private Student readForm() {
        String si = id.getText().trim();
        String sn = name.getText().trim();
        String sc = course.getText().trim();
        String sm = marks.getText().trim();

        if (si.isEmpty() || sn.isEmpty()
                || sc.isEmpty() || sm.isEmpty()) {
            error("Please fill in all fields.");
            return null;
        }

        try {
            int studentId = Integer.parseInt(si);
            double studentMarks = Double.parseDouble(sm);

            if (studentId <= 0) {
                error("Student ID must be a positive number.");
                return null;
            }

            if (studentMarks < 0 || studentMarks > 100) {
                error("Marks must be between 0 and 100.");
                return null;
            }

            return new Student(
                    studentId, sn, sc, studentMarks);

        } catch (NumberFormatException ex) {
            error("Enter a valid student ID and numeric marks.");
            return null;
        }
    }

    private Student find(int studentId) {
        for (Student s : students) {
            if (s.id == studentId) return s;
        }
        return null;
    }

    private void addStudent() {
        Student s = readForm();
        if (s == null) return;

        if (find(s.id) != null) {
            error("This Student ID already exists.");
            return;
        }

        students.add(s);
        refresh();
        clearFields();
        status.setText("Student added successfully.");
    }

    private void updateStudent() {
        int view = table.getSelectedRow();

        if (view < 0) {
            error("Select a student row first.");
            return;
        }

        int row = table.convertRowIndexToModel(view);
        int oldId = Integer.parseInt(
                model.getValueAt(row, 0).toString());

        Student old = find(oldId);
        Student s = readForm();

        if (old == null || s == null) return;

        Student duplicate = find(s.id);
        if (duplicate != null && duplicate != old) {
            error("Another student already uses this ID.");
            return;
        }

        old.id = s.id;
        old.name = s.name;
        old.course = s.course;
        old.marks = s.marks;

        refresh();
        clearFields();
        status.setText("Student updated successfully.");
    }

    private void deleteStudent() {
        int view = table.getSelectedRow();

        if (view < 0) {
            error("Select a student row first.");
            return;
        }

        int row = table.convertRowIndexToModel(view);
        int studentId = Integer.parseInt(
                model.getValueAt(row, 0).toString());

        Student s = find(studentId);

        if (s != null && JOptionPane.showConfirmDialog(
                this, "Delete the record for " + s.name + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            students.remove(s);
            deletedStudents.add(s);
            refresh();
            clearFields();
            status.setText("Student deleted.");
        }
    }

    // Restores the most recently deleted student.
    private void restoreDeletedStudent() {
        if (deletedStudents.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "There are no deleted student records to restore.",
                    "Restore Student", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Student s = deletedStudents.get(deletedStudents.size() - 1);
        if (find(s.id) != null) {
            error("Cannot restore Student ID " + s.id
                    + " because that ID is already in use. Remove or change the existing record first.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this, "Restore the deleted record for " + s.name + " (ID: " + s.id + ")?",
                "Restore Student", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            students.add(s);
            deletedStudents.remove(deletedStudents.size() - 1);
            refresh();
            clearFields();
            status.setText("Deleted student restored successfully.");
        }
    }

    private void loadSelected() {
        int view = table.getSelectedRow();
        if (view < 0) return;

        int row = table.convertRowIndexToModel(view);

        id.setText(model.getValueAt(row, 0).toString());
        name.setText(model.getValueAt(row, 1).toString());
        course.setText(model.getValueAt(row, 2).toString());
        marks.setText(model.getValueAt(row, 3).toString());
    }

    private void refresh() {
        model.setRowCount(0);

        for (Student s : students) {
            model.addRow(new Object[]{
                    s.id, s.name, s.course,
                    String.format(java.util.Locale.US, "%.2f", s.marks),
                    s.grade()
            });
        }

        count.setText("Total Students: " + students.size());
        filter();
    }

    private void filter() {
        String q = search.getText().trim();

        sorter.setRowFilter(q.isEmpty() ? null :
                RowFilter.regexFilter(
                        "(?i)" + java.util.regex.Pattern.quote(q)));
    }

    private void clearFields() {
        id.setText("");
        name.setText("");
        course.setText("");
        marks.setText("");
        table.clearSelection();
        id.requestFocusInWindow();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Please Check",
                JOptionPane.ERROR_MESSAGE);
        status.setText(message);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Use the default Swing style if unavailable.
        }

        SwingUtilities.invokeLater(
                () -> new StudentManagementSystem().setVisible(true));
    }
}
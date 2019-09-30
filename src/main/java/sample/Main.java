package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {

    ScrollPane sc0 = new ScrollPane();
    ScrollPane sc1 = new ScrollPane();
    GridPane gp1 = new GridPane();
    ScrollPane sc2 = new ScrollPane();
    GridPane gp2 = new GridPane();
    ScrollPane sc3 = new ScrollPane();
    GridPane gp3 = new GridPane();
    ScrollPane sc4 = new ScrollPane();
    GridPane gp4 = new GridPane();
    Person person = new Person();
    Label notif = new Label("");

    Stage stage;

    static class Counter
    {
        public int n = 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.stage = primaryStage;
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(15);

        Label resumeL = new Label("Resume Builder");
        resumeL.setStyle("-fx-font-size: 24");
        Button button1 = new Button("Build new Resume!");
        Button button2 = new Button("Build from old data!");
        vBox.getChildren().addAll(resumeL, button1, button2);
        vBox.setAlignment(Pos.CENTER);
        button1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                getPersonalInfo();
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                person.searchForResume(primaryStage);
            }
        });

        borderPane.setCenter(vBox);
        Scene scene1 = new Scene(borderPane, 400, 400);
        scene1.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene1);
        stage.setTitle("Resume Builder");
        stage.show();
    }

    void getPersonalInfo()
    {
        notif.setText("");
        int rowCounter = 0;
        Button nextBtn = new Button("Next");

        gp1.addRow(rowCounter++, person.nameL, person.nameTF);
        gp1.addRow(rowCounter++, person.professionL, person.professionTF);
        gp1.addRow(rowCounter++, person.addressL, person.addressTF);
        gp1.addRow(rowCounter++, person.phoneL, person.phoneTF);
        gp1.addRow(rowCounter++, person.emailL, person.emailTF);
        gp1.addRow(rowCounter++, nextBtn);
        //gp1.addRow(rowCounter++, notif);
        VBox vBox = new VBox();

        sc1.setContent(gp1);
        vBox.getChildren().addAll(sc1, notif);

        nextBtn.setOnAction(event ->
        {
            if(person.validatePersonalInfo())
            {
                getEducation();
            }
            else
            {
                notif.setText("Some field/s is/are invalid. \nPlease fill the fields according to the following constraints:\n1. Phone number should be 10 digit. \n2. Email ID should be proper. \n3. No field should be blank.");
            }
        });

        Scene scene1 = new Scene(vBox, 400, 400);
        scene1.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene1);
        stage.setTitle("Personal Info");
        stage.show();
    }

    void getEducation()
    {
        notif.setText("");
        int rowCounter = 0;
        Button nextBtn = new Button("Next");

        gp2.addRow(rowCounter++, person.schoolL, person.schoolTF);
        gp2.addRow(rowCounter++, person.SSC_L, person.SSC_TF);
        gp2.addRow(rowCounter++, person.juniorCollegeL, person.juniorCollegeTF);
        gp2.addRow(rowCounter++, person.HSC_L, person.HSC_TF);
        gp2.addRow(rowCounter++, person.universityNameL, person.universityNameTF);
        gp2.addRow(rowCounter++, person.UndergradCGPA_L, person.UndergradCGPA_TF);
        gp2.addRow(rowCounter++, nextBtn);
        //gp2.addRow(rowCounter++, notif);
        sc2.setContent(gp2);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(sc2, notif);

        nextBtn.setOnAction(event ->
        {
            if(person.validateEducation())
            {
                getSkills();
            }
            else
            {
                notif.setText("Some field/s is/are invalid. \nPlease fill the fields according to the following constraints:\n1. SSC & HSC percent should be between 1 and 100. \n2. CGPA should be between 1 and 10. \n3. No field should be blank.");
            }
        });


        Scene scene = new Scene(vBox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Educational Details");
        stage.show();
    }

    void getSkills()
    {
        notif.setText("");
        final Counter count = new Counter();
        Button nextBtn = new Button("Next");
        Button addBtn = new Button("Add skill");

        gp3.addRow(count.n++, notif);
        gp3.addRow(count.n++, addBtn, nextBtn);

        sc3.setContent(gp3);

        addBtn.setOnAction(actionEvent ->
        {
            TextField newSkillTF = new TextField();
            person.skillsArr.add(newSkillTF);
            gp3.addRow(count.n++,newSkillTF);
        });

        nextBtn.setOnAction(event ->
        {
            if(person.validateSkills())
            {
                getCoverLetter();
            }
            else
            {
                notif.setText("Skills should not be blank.");
            }
        });

        Scene scene = new Scene(sc3, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Skills");
        stage.show();
    }

    void getCoverLetter()
    {
        notif.setText("");
        Button submitBtn = new Button("Submit");
        person.coverletterTF.setPrefSize(350,50);
        person.coverletterTF.setMaxSize(350, 50);
        gp4.addRow(0, person.coverletterL);
        gp4.addRow(1, person.coverletterTF);
        gp4.addRow(2, submitBtn);
        gp4.addRow(3, notif);

        submitBtn.setOnAction(event ->
        {
            if(person.validateCoverLetter())
            {
                try
                {
                    person.databaseConnection();
                    person.process();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                } catch (DocumentException e)
                {
                    e.printStackTrace();
                }

                BorderPane borderPane = new BorderPane();
                Label success = new Label("Your resume has been generated in the file resume.html");
                borderPane.setCenter(success);
                Scene scene = new Scene(borderPane, 400, 400);
                stage.setScene(scene);
                stage.setTitle("Resume Generated");
                stage.show();
            }
            else
            {
                notif.setText("Cover letter should not be empty or above 200 letters.");
            }
        });

        sc4.setContent(gp4);

        Scene scene = new Scene(sc4, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Cover letter");
        stage.show();
    }



    public static void main(String[] args)
    {
        launch(args);
    }
}

class Person
{
    //Personal Info
    Label nameL = new Label("Name: ");
    TextField nameTF = new TextField();
    Label professionL = new Label("Profession: ");
    TextField professionTF = new TextField();
    Label addressL = new Label("Address: ");
    TextField addressTF = new TextField();
    Label phoneL = new Label("Phone: ");
    TextField phoneTF = new TextField();
    Label emailL = new Label("Email: ");
    TextField emailTF = new TextField();

    //Education
    Label schoolL = new Label("Name of school: ");
    TextField schoolTF = new TextField();
    Label SSC_L = new Label("SSC Percentage: ");
    TextField SSC_TF = new TextField();
    Label juniorCollegeL = new Label("Junior college: ");
    TextField juniorCollegeTF = new TextField();
    Label HSC_L = new Label("HSC Percentage: ");
    TextField HSC_TF = new TextField();
    Label universityNameL = new Label("University for undergrad: ");
    TextField universityNameTF = new TextField();
    Label UndergradCGPA_L = new Label("Undergrad CGPA: ");
    TextField UndergradCGPA_TF = new TextField();

    //Skills
    ArrayList<TextField> skillsArr = new ArrayList<>();

    //Cover Letter
    Label coverletterL = new Label("Cover Letter");
    TextField coverletterTF = new TextField();

    Validation validation = new Validation();

    TextField nameSearchTF = new TextField();
    String nameSearchString;
    Label notif = new Label("");

    float calculatePercent(int marks, int total)
    {
        float percent = (float)marks/total * 100;
        return percent;
    }

    void databaseConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stqa","root","root");

            String skills = "";
            for(TextField iter: skillsArr)
            {
                skills += iter.getText() + ", ";
            }

            String query = "insert into Person values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, nameTF.getText());
            preparedStmt.setString(2, professionTF.getText());
            preparedStmt.setString(3, addressTF.getText());
            preparedStmt.setLong(4, Long.parseLong(phoneTF.getText()));
            preparedStmt.setString(5, emailTF.getText());
            preparedStmt.setString(6, schoolTF.getText());
            preparedStmt.setFloat(7, Float.parseFloat(SSC_TF.getText()));
            preparedStmt.setString(8, juniorCollegeTF.getText());
            preparedStmt.setFloat(9, Float.parseFloat(HSC_TF.getText()));
            preparedStmt.setString(10, universityNameTF.getText());
            preparedStmt.setFloat(11, Float.parseFloat(UndergradCGPA_TF.getText()));
            preparedStmt.setString(12, skills);
            preparedStmt.setString(13, coverletterTF.getText());

            preparedStmt.execute();
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    void searchForResume(Stage stage)
    {
        Label nameSearchL = new Label("Enter name: ");
        HBox hBox = new HBox(10);
        Button searchBtn = new Button("Search");
        VBox vBox = new VBox();
        hBox.getChildren().addAll(nameSearchL, nameSearchTF);
        vBox.getChildren().addAll(hBox, searchBtn, notif);
        Scene scene = new Scene(vBox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Enter name");
        stage.show();
        nameSearchString = nameSearchTF.getText();

        searchBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                retrieveData(nameSearchString);
            }
        });
    }

    String retrieveData(String name)
    {
        try
        {
            notif.setText("");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stqa","root","root");

            Statement stmt = connection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Person where name=\"" + name + "\";");
            if(!rs.next())
            {
                notif.setText("Such record does not exist. Reenter record name");
                return null;
            }
            else
            {
                //ResultSet rs=stmt.executeQuery("select * from Person");
                System.out.println("NameSearchString: " + name);
                //System.out.println(rs.getString(1));
                System.out.println("Debug");
                do
                {
                    nameTF.setText(rs.getString(1));
                    System.out.println(rs.getString(1));
                    professionTF.setText(rs.getString(2));
                    addressTF.setText(rs.getString(3));
                    phoneTF.setText(Long.toString(rs.getLong(4)));
                    emailTF.setText(rs.getString(5));
                    schoolTF.setText(rs.getString(6));
                    SSC_TF.setText(Float.toString(rs.getFloat(7)));
                    juniorCollegeTF.setText(rs.getString(8));
                    HSC_TF.setText(Float.toString(rs.getFloat(9)));
                    universityNameTF.setText(rs.getString(10));
                    UndergradCGPA_TF.setText(Float.toString(rs.getFloat(11)));
                    String skillsString = rs.getString(12);
                    coverletterTF.setText(rs.getString(13));

                    String[] skillsArray = skillsString.split(",");
                    for(int i = 0; i < skillsArray.length; i++)
                    {
                        skillsArr.add(new TextField(skillsArray[i]));
                    }
                }while (rs.next());

                System.out.println("NameTF: " + nameTF.getText());
                System.out.println("ProfessionTF: " + professionTF.getText());

                process();
                notif.setText(name + ", your resume is now available in resume.html!");
                return emailTF.getText();
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }



     boolean setUpValidation(final TextField tf)
     {
        tf.textProperty().addListener((observable, oldValue, newValue) -> validate(tf));
        return validate(tf);
     }

     boolean validate(TextField tf)
     {
         boolean isCorrect = true;
        ObservableList<String> styleClass = tf.getStyleClass();

         if (tf.getText().trim().length() == 0)
         {
             isCorrect = false;

             if (!styleClass.contains("error"))
             {
                 styleClass.add("error");
             }
         }
         else
         {
             // remove all occurrences:
             styleClass.removeAll(Collections.singleton("error"));
         }

         return isCorrect;
    }

    boolean validatePersonalInfo()
    {
        if(setUpValidation(nameTF) & setUpValidation(professionTF) & setUpValidation(addressTF) & setUpValidation(emailTF) & setUpValidation(phoneTF))
        {
            if(validation.validatePhone(phoneTF.getText()) & validation.validateEmail(emailTF.getText()))
            {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }


    boolean validateEducation()
    {
        if(setUpValidation(schoolTF) & setUpValidation(juniorCollegeTF) & setUpValidation(universityNameTF) & setUpValidation(SSC_TF) & setUpValidation(HSC_TF) & setUpValidation(UndergradCGPA_TF))
        {
            if(validation.validateCGPA(UndergradCGPA_TF.getText()) & validation.validateSSCPercent(SSC_TF.getText()))
            {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    boolean validateSkills()
    {
        for (TextField iter: skillsArr)
        {
            if(!setUpValidation(iter))
            {
                return false;
            }
        }

        return true;
    }

    boolean validateCoverLetter()
    {
        if(setUpValidation(coverletterTF))
        {
            if(validation.validateCoverLetter(coverletterTF.getText()))
            {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    void process()throws IOException, DocumentException
    {
        File htmlTemplateFile = new File("C:\\Users\\Rachana\\Desktop\\Programs\\Java_Programs\\STQA\\ResumeBuilder2\\src\\main\\java\\sample\\resumeTemplate.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);

        htmlString = htmlString.replace("$name", nameTF.getText());
        htmlString = htmlString.replace("$profession", professionTF.getText());
        htmlString = htmlString.replace("$address", addressTF.getText());
        htmlString = htmlString.replace("$email", emailTF.getText());
        htmlString = htmlString.replace("$phone", phoneTF.getText());

        htmlString = htmlString.replace("$university", universityNameTF.getText());
        htmlString = htmlString.replace("$juniorCollege", juniorCollegeTF.getText());
        htmlString = htmlString.replace("$school", schoolTF.getText());
        htmlString = htmlString.replace("$cgpa", UndergradCGPA_TF.getText());
        htmlString = htmlString.replace("$HSC_Percent", HSC_TF.getText());
        htmlString = htmlString.replace("$SSC_Percent", SSC_TF.getText());

        htmlString = htmlString.replace("$coverLetter", coverletterTF.getText());
        String Skills = "<ul>";

        for(TextField iter: skillsArr)
        {
            Skills += "<li>" + iter.getText() + "</li>";
        }

        Skills += "</ul>";
        htmlString = htmlString.replace("$skills", Skills);

        File newHtmlFile = new File("C:\\Users\\Rachana\\Desktop\\Programs\\Java_Programs\\STQA\\ResumeBuilder2\\src\\main\\java\\sample\\resume.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

}

class Validation
{
    boolean validatePhone(String phoneString)
    {
        //String phoneString = phoneTF.getText();

        if(phoneString.length() == 10)
        {
            try
            {
                Long phoneNumber = Long.parseLong(phoneString);
                return true;
            }
            catch (NumberFormatException ex)
            {
                return false;
            }
        }

        return false;

    }

    boolean validateEmail(String emailString)
    {
        if(emailString.matches(".*@.*\\..*"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    boolean validateCGPA(String CGPAString)
    {
        try
        {
            Float CGPA = Float.parseFloat(CGPAString);
            if(CGPA >= 0 && CGPA <= 10)
            {
                return true;
            }
        }
        catch (NumberFormatException ex)
        {
            return false;
        }

        return false;
    }

    boolean validateSSCPercent(String SSCPercentString)
    {
        try
        {
            Float SSCPercent = Float.parseFloat(SSCPercentString);
            if(SSCPercent >= 0 && SSCPercent <= 100)
            {
                return true;
            }
        }
        catch (NumberFormatException ex)
        {
            return false;
        }

        return false;
    }

    boolean validateCoverLetter(String CoverLetterString)
    {
        if(CoverLetterString.length() < 200)
        {
            return true;
        }
        else
            return false;
    }

}




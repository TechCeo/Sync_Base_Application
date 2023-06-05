package com.xxm.syncbase;


import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.bankfiles.service.BookService;
import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import com.xxm.syncbase.application.fileobjects.service.BookObjectService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {
    @Value("${syncbase.files.directory}")
    private String fileDirectory;
    @Value("${syncbase.files.config_file1}")
    private String configFile1;
    @Value("${syncbase.files.config_file2}")
    private String configFile2;

    public Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookService bookService, BookObjectService feedsService, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            this.configureBankBooks(this.configFile1, bookService);
            this.configureBankBookObjects(this.configFile2, bookService, feedsService);
            this.configureSystemUser(applicationUserRepository, passwordEncoder);
        };
    }

    private void configureBankBooks(String fileName, BookService bookService) {
        File file = this.getFileFromPath(fileName);

        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            String[] line;
            for(long cnt = 0L; (line = reader.readNext()) != null; ++cnt) {
                if (cnt != 0L) {
                    List<String> datas = Arrays.asList(line);
                    BankBookDTO bankBookDTO = new BankBookDTO();
                    bankBookDTO.setBookName(datas.get(0));
                    bankBookDTO.setBookPrefix(datas.get(1));
                    bankBookDTO.setMappedTableName(datas.get(2));
                    bankBookDTO.setEnabled(true);
                    BankBookDTO check = bookService.getBookByName(bankBookDTO.getBookName());
                    if (check == null) {
                        bookService.addBook(bankBookDTO);
                    } else {
                        bookService.updateBook(bankBookDTO);
                    }

                    System.out.println(datas);
                }
            }

            reader.close();
        } catch (IOException var11) {
            var11.printStackTrace();
        } catch (Exception var12) {
            var12.printStackTrace();
            throw new SyncBaseException("Error Occurred configuring book " + var12.getMessage());
        }

    }

    private void configureSystemUser(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        String adminName = "admin";
        SystemUser systemUser = userRepository.findByUserName(adminName);
        if (systemUser == null) {
            systemUser = new SystemUser();
            systemUser.setEmail("admin@testmail.com");
            systemUser.setFirstName("admin");
            systemUser.setLastName("user");
            systemUser.setUserName(adminName);
            systemUser.setPassword(passwordEncoder.encode("password@123"));
            userRepository.save(systemUser);
        }

    }

    private void configureBankBookObjects(String fileName, BookService bookService, BookObjectService feedsService) {
        File file = this.getFileFromPath(fileName);

        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            String[] line;
            for(long cnt = 0L; (line = reader.readNext()) != null; ++cnt) {
                if (cnt != 0L) {
                    List<String> datas = Arrays.asList(line);
                    BookObjectDTO feedObjectDTO = new BookObjectDTO();
                    BankBookDTO bankBookDTO = bookService.getBookByName(datas.get(0));
                    if (bankBookDTO == null) {
                        throw new SyncBaseException("Invalid Book Name at row : " + cnt);
                    }

                    feedObjectDTO.setBankBookDTO(bankBookDTO);
                    feedObjectDTO.setName(datas.get(1).trim());
                    feedObjectDTO.setDescription(datas.get(2).trim());
                    feedObjectDTO.setMappedColumn(datas.get(3).trim());
                    feedObjectDTO.setMappedColumnType(datas.get(4).trim());
                    feedObjectDTO.setDefaultValue(datas.get(5).trim());
                    BookObjectDTO check = feedsService.getFeedByNameAndBookname(feedObjectDTO.getName(), feedObjectDTO.getBankBookDTO().getBookName());
                    if (check == null) {
                        feedsService.addBookObject(feedObjectDTO);
                    } else {
                        feedsService.updateBookObject(feedObjectDTO);
                    }
                }
            }

            reader.close();
        } catch (IOException var13) {
            var13.printStackTrace();
            throw new SyncBaseException("Error Occurred configuring book objects" + var13.getMessage());
        } catch (Exception var14) {
            var14.printStackTrace();
            throw new SyncBaseException("Error Occurred configuring book objects" + var14.getMessage());
        }
    }

    private File getFileFromPath(String fileName) {
        System.out.println("File search path :: " + this.fileDirectory);
        File file = new File(this.fileDirectory + fileName);
        if (!file.exists()) {
            File folder = new File(this.fileDirectory);
            File[] folderItems = folder.listFiles();
            File[] var6 = folderItems;
            int var7 = folderItems.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                File f = var6[var8];
                if (f.isFile() && f.getName().matches(fileName)) {
                    file = f;
                }
            }
        }

        return file;
    }
}

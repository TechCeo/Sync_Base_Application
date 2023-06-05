package com.xxm.syncbase.web.books;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.bankfiles.service.BookService;
import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import com.xxm.syncbase.application.fileobjects.service.BookObjectService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/books"})
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    BookService bookService;
    @Autowired
    BookObjectService bookObjectService;

    public BookController() {
    }

    @GetMapping({""})
    public String index(Model model) {
        List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
        model.addAttribute("books", bankBookDTOS);
        return "books/list";
    }

    @GetMapping({"/add"})
    public String addBookView(Model model) {
        model.addAttribute("book", new BankBookDTO());
        return "books/add";
    }

    @RequestMapping(
            value = {"/add"},
            method = {RequestMethod.POST}
    )
    public String addBook(@ModelAttribute("book") @Valid BankBookDTO bankBookDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        logger.info("BankBook details: {}", bankBookDTO);

        try {
            this.bookService.addBook(bankBookDTO);
            model.addAttribute("successMessage", "Successfully added Book");
            model.addAttribute("book", new BankBookDTO());
        } catch (SyncBaseException var7) {
            model.addAttribute("errorMessage", var7.getMessage());
        } catch (Exception var8) {
            model.addAttribute("errorMessage", var8.getMessage());
        }

        return "books/add";
    }

    @RequestMapping(
            value = {"/bulk/upload"},
            method = {RequestMethod.POST}
    )
    public String uploadBooks(@RequestParam("uploadFile") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            logger.info("Empty file");
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload");
            return "redirect:/books/add";
        } else {
            try {
                this.bookService.uploadBooks(file);
                model.addAttribute("successMessage", "Successfully Uploaded Books");
            } catch (SyncBaseException var5) {
                model.addAttribute("errorMessage", var5.getMessage());
            } catch (Exception var6) {
                model.addAttribute("errorMessage", var6.getMessage());
            }

            List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
            model.addAttribute("books", bankBookDTOS);
            return "books/list";
        }
    }

    @GetMapping({"/{id}"})
    public String getBookView(@PathVariable("id") Long id, Model model) {
        BankBookDTO bankBookDTO = this.bookService.getBookById(id);
        model.addAttribute("book", bankBookDTO);
        return "books/edit";
    }

    @RequestMapping(
            value = {"/{id}/edit"},
            method = {RequestMethod.POST}
    )
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") @Valid BankBookDTO bankBookDTO, Model model) {
        logger.info("BankBook details: {}", bankBookDTO);

        try {
            bankBookDTO = this.bookService.updateBook(bankBookDTO);
            model.addAttribute("successMessage", "Successfully Updated Book");
        } catch (SyncBaseException var5) {
            model.addAttribute("errorMessage", var5.getMessage());
            bankBookDTO = this.bookService.getBookById(id);
        } catch (Exception var6) {
            model.addAttribute("errorMessage", var6.getMessage());
            bankBookDTO = this.bookService.getBookById(id);
        }

        model.addAttribute("book", bankBookDTO);
        return "books/edit";
    }

    @RequestMapping(
            value = {"/{id}/delete"},
            method = {RequestMethod.GET}
    )
    public String deleteBook(@PathVariable("id") Long id, Model model, Locale locale) {
        try {
            this.bookService.deleteBook(id);
            model.addAttribute("successMessage", "Successfully Deleted Book");
        } catch (SyncBaseException var5) {
            model.addAttribute("errorMessage", var5.getMessage());
        } catch (Exception var6) {
            model.addAttribute("errorMessage", var6.getMessage());
        }

        List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
        model.addAttribute("books", bankBookDTOS);
        return "books/list";
    }

    @GetMapping({"/{bankBookId}/objects"})
    public String getBookObjectsView(@PathVariable("bankBookId") Long bankBookId, Model model, RedirectAttributes redirectAttributes) {
        List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
        model.addAttribute("bookobjects", feedObjectDTOList);
        model.addAttribute("bookId", bankBookId);
        return "bookobjects/list";
    }

    @GetMapping({"/{bankBookId}/objects/add"})
    public String addBookObjectsView(@PathVariable("bankBookId") Long bankBookId, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("bookobject", new BookObjectDTO());
        model.addAttribute("bookId", bankBookId);
        return "bookobjects/add";
    }

    @RequestMapping(
            value = {"/{bankBookId}/objects/add"},
            method = {RequestMethod.POST}
    )
    public String addBookObject(@PathVariable("bankBookId") Long bankBookId, @ModelAttribute("bookobject") @Valid BookObjectDTO bookObjectDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        logger.info("bookObjectDTO details: {}", bookObjectDTO);

        try {
            BankBookDTO bankBookDTO = this.bookService.getBookById(bankBookId);
            bookObjectDTO.setBankBookDTO(bankBookDTO);
            this.bookObjectService.addBookObject(bookObjectDTO);
            List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
            model.addAttribute("bookobjects", feedObjectDTOList);
            model.addAttribute("bookId", bankBookId);
            model.addAttribute("successMessage", "Successfully Added Object");
            return "bookobjects/list";
        } catch (SyncBaseException var9) {
            var9.printStackTrace();
            model.addAttribute("errorMessage", var9.getMessage());
        } catch (Exception var10) {
            var10.printStackTrace();
            model.addAttribute("errorMessage", var10.getMessage());
        }

        model.addAttribute("bookobject", bookObjectDTO);
        model.addAttribute("bookId", bankBookId);
        return bankBookId + "bookobjects/add";
    }

    @RequestMapping(
            value = {"/{bookId}/objects/bulk/upload"},
            method = {RequestMethod.POST}
    )
    public String uploadBookObjects(@PathVariable("bookId") Long bookId, @RequestParam("uploadFile") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            logger.info("Empty Object file");
            model.addAttribute("errorMessage", "Please select a file to upload");
        } else {
            try {
                this.bookObjectService.uploadBookObjects(file, bookId);
                model.addAttribute("successMessage", "Successfully Uploaded Books");
            } catch (SyncBaseException var6) {
                model.addAttribute("errorMessage", var6.getMessage());
            } catch (Exception var7) {
                model.addAttribute("errorMessage", var7.getMessage());
            }
        }

        List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bookId);
        model.addAttribute("bookobjects", feedObjectDTOList);
        model.addAttribute("bookId", bookId);
        return "bookobjects/list";
    }

    @GetMapping({"/{bankBookId}/objects/{bookObjectId}"})
    public String getBookObjectView(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, Model model, RedirectAttributes redirectAttributes) {
        BookObjectDTO feedObjectDTO = this.bookObjectService.getBookObjectById(bookObjectId);
        model.addAttribute("bookobject", feedObjectDTO);
        model.addAttribute("bookId", bankBookId);
        return "bookobjects/edit";
    }

    @RequestMapping(
            value = {"/{bankBookId}/objects/{bookObjectId}"},
            method = {RequestMethod.POST}
    )
    public String editBookObject(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, @ModelAttribute("bookobject") @Valid BookObjectDTO bookObjectDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        logger.info("bookObjectDTO details: {}", bookObjectDTO);

        try {
            BankBookDTO bankBookDTO = this.bookService.getBookById(bankBookId);
            if (bankBookDTO == null) {
                throw new SyncBaseException("Book Not Found :: " + bankBookId);
            }

            bookObjectDTO.setBankBookDTO(bankBookDTO);
            this.bookObjectService.updateBookObject(bookObjectDTO);
            model.addAttribute("successMessage", "Successfully Updated Object");
        } catch (SyncBaseException var9) {
            var9.printStackTrace();
            model.addAttribute("errorMessage", var9.getMessage());
        } catch (Exception var10) {
            var10.printStackTrace();
            model.addAttribute("errorMessage", var10.getMessage());
        }

        BookObjectDTO bookObjectDTO1 = this.bookObjectService.getBookObjectById(bookObjectId);
        model.addAttribute("bookobjects", bookObjectDTO1);
        model.addAttribute("bookId", bankBookId);
        return "bookobjects/edit";
    }

    @RequestMapping(
            value = {"/{bankBookId}/objects/{bookObjectId}/delete"},
            method = {RequestMethod.GET}
    )
    public String deleteBookObject(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, Model model, Locale locale) {
        try {
            this.bookObjectService.deleteBookObject(bookObjectId);
            model.addAttribute("successMessage", "Successfully Deleted Object");
        } catch (SyncBaseException var6) {
            var6.printStackTrace();
            model.addAttribute("errorMessage", var6.getMessage());
        } catch (Exception var7) {
            var7.printStackTrace();
            model.addAttribute("errorMessage", var7.getMessage());
        }

        List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
        model.addAttribute("bookobjects", feedObjectDTOList);
        model.addAttribute("bookId", bankBookId);
        return "bookobjects/list";
    }
}

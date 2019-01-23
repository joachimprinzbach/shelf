package com.joachim.books.shelf.googlebooks;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Volume;
import com.joachim.books.shelf.model.Book;
import com.joachim.books.shelf.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleBooksService {

    @Value("${google.books.appname}")
    private String appName;
    @Value("${google.books.token}")
    private String apiToken;
    @Value("${google.books.shelfid}")
    private String shelfId = "1001";
    @Value("${google.books.userid}")
    private String userId = "115315262558340554382";

    private final BookRepository bookRepository;

    void queryGoogleBooks() {
        try {
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName(appName)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiToken))
                    .build();

            long currentTimeLong = new Date().getTime();
            Bookshelf shelf = books.bookshelves().get(userId, shelfId).execute();
            //long lastUpdatedLong = shelf.getVolumesLastUpdated().getValue();
            // TODO: Only continue if shelf has been updated during the last 2 minutes

            HashSet<Volume> volumes = new HashSet<>();
            for (int i = 0; i < shelf.getVolumeCount(); i = i + 40) {
                List<Volume> loadedPagedVolumes = books.bookshelves().volumes().list(userId, shelfId).setMaxResults(40L).setStartIndex((long) i).execute().getItems();
                volumes.addAll(loadedPagedVolumes);
            }

            for (Volume volume : volumes) {
                String isbn = getISBN(volume);
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                if (!bookRepository.findBookByIsbn(isbn).isPresent()) {
                    Book of = Book.of(isbn, volumeInfo.getTitle(), volumeInfo.getSubtitle(), volumeInfo.getDescription(), volumeInfo.getImageLinks().getThumbnail(), volumeInfo.getPageCount());
                    of.publishBookCreated();
                    bookRepository.save(of);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getISBN(Volume volume) {
        List<Volume.VolumeInfo.IndustryIdentifiers> industryIdentifiers = volume.getVolumeInfo().getIndustryIdentifiers();
        for (Volume.VolumeInfo.IndustryIdentifiers industryIdentifier : industryIdentifiers) {
            if ("ISBN_13".equals(industryIdentifier.getType()))
                return industryIdentifier.getIdentifier();
        }
        return "";
    }

}

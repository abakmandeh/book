package id.agit.book.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BorrowRequest {

    private String id;

    private String peminjam;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date tanggalKembali;
}

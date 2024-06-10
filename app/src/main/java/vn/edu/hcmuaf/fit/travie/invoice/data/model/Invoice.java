package vn.edu.hcmuaf.fit.travie.invoice.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie.hotel.model.BookingType;
import vn.edu.hcmuaf.fit.travie.hotel.model.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseModel {
    private String code;
    //    private AppUser user;
    private Room room;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int totalPrice;
    private int finalPrice;
    private BookingType bookingType;
    private PaymentMethod paymentMethod;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;

    public Invoice(long id, String code, Room room, LocalDateTime checkIn, LocalDateTime checkOut,
                   int totalPrice, int finalPrice, BookingType bookingType, PaymentMethod paymentMethod, BookingStatus bookingStatus, PaymentStatus paymentStatus) {
        super(id, LocalDateTime.now(), LocalDateTime.now());
        this.code = code;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.finalPrice = finalPrice;
        this.bookingType = bookingType;
        this.paymentMethod = paymentMethod;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
    }

    public Invoice(Parcel in) {
        super(in);
        code = in.readString();
        room = in.readParcelable(Room.class.getClassLoader());
        totalPrice = in.readInt();
        finalPrice = in.readInt();
        paymentMethod = PaymentMethod.valueOf(in.readString());
        bookingStatus = BookingStatus.valueOf(in.readString());
        paymentStatus = PaymentStatus.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(code);
        dest.writeParcelable(room, flags);
        dest.writeInt(totalPrice);
        dest.writeInt(finalPrice);
        dest.writeString(paymentMethod.name());
        dest.writeString(bookingStatus.name());
        dest.writeString(paymentStatus.name());
    }

    public static final Creator<Invoice> CREATOR = new Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

    public static Invoice demo1() {
        LocalDateTime now = LocalDateTime.now();
        return new Invoice(1L, "INV001", Room.demo1(), now, now.plusDays(1), 100, 90,
         BookingType.demo3(), PaymentMethod.CASH, BookingStatus.CONFIRMED, PaymentStatus.PAID);
    }

    public static Invoice demo2() {
        LocalDateTime now = LocalDateTime.now();
        return new Invoice(2L, "INV002", Room.demo2(), now, now.plusDays(2), 200, 180,
                BookingType.demo3(), PaymentMethod.CASH, BookingStatus.CONFIRMED, PaymentStatus.PAID);
    }

    public static Invoice demo3() {
        LocalDateTime now = LocalDateTime.now();
        return new Invoice(3L, "INV003", Room.demo1(), now, now.plusDays(3), 300, 270,
                BookingType.demo3(), PaymentMethod.CASH, BookingStatus.CONFIRMED, PaymentStatus.PAID);
    }

    public static List<Invoice> demo() {
        return new ArrayList<Invoice>() {{
            add(demo1());
            add(demo2());
            add(demo3());
        }};
    }
}

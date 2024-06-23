package vn.edu.hcmuaf.fit.travie.invoice.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseModel {
    private String code;
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
        checkIn = LocalDateTime.parse(in.readString());
        checkOut = LocalDateTime.parse(in.readString());
        totalPrice = in.readInt();
        finalPrice = in.readInt();
        bookingType = in.readParcelable(BookingType.class.getClassLoader());
        paymentMethod = PaymentMethod.valueOf(in.readString());
        bookingStatus = BookingStatus.valueOf(in.readString());
        paymentStatus = PaymentStatus.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(code);
        dest.writeParcelable(room, flags);
        dest.writeString(checkIn.toString());
        dest.writeString(checkOut.toString());
        dest.writeInt(totalPrice);
        dest.writeInt(finalPrice);
        dest.writeParcelable(bookingType, flags);
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
}

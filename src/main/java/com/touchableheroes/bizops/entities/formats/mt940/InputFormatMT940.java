package com.touchableheroes.bizops.entities.formats.mt940;

import de.siegmar.fastcsv.reader.CsvRow;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.*;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(
        name = "input_mt940"
)
public class InputFormatMT940 {


    @Id
    @GeneratedValue
    private Long id;

    //@NotBlank
    //@NotNull
    @Column(
            name = "Auftragskonto",
            length = 256
    )
    //@Size(max = 1024)
    private String auftragsKonto;

        @Column(
            name = "Buchungstag",
            unique=false
    )
    //@Size(max = 1024)
    private Date buchungsTag;

    @Column(
            name = "Valutadatum",
            unique=false
    )
    //@Size(max = 1024)
    private Date valutaDatum;

    @Column(
            name = "Buchungstext",
            unique=false,
            length = 1024
    )
    private String buchungsText;

    @Column(
            name = "Verwendungszweck",
            unique=false,
            length = 4096
    )
    private String verwendungsZweck;


    @Column(
            name = "Beguenstigter_Zahlungspflichtiger",
            unique=false
    )
    private String beguenstigterZahlungsPflichtiger;

    @Column(
            name = "Kontonummer",
            unique=false
    )
    private String kontonummer;

    @Column(
            name = "BLZ",
            unique=false
    )
    private String blz;

    @Column(
            name = "Betrag",
            unique=false
    )
    private Double betrag;


    @Column(
            name = "Waehrung",
            unique=false
    )
    private String waehrung;


    @Column(
            name = "Info",
            unique=false
    )
    private String info;


    public InputFormatMT940(){}


    public static InputFormatMT940 map(CsvRow row) {
        InputFormatMT940 rval = new InputFormatMT940();

        rval.setAuftragsKonto(
                row.getField("Auftragskonto")
        );

        rval.setBuchungsTag(
                parseDate( row.getField("Buchungstag" ) )
        );

        rval.setValutaDatum(
                parseDate( row.getField("Valutadatum" ) )
        );

        rval.setBuchungsText(
                row.getField("Buchungstext")
        );

        rval.setVerwendungsZweck(
                row.getField("Verwendungszweck")
        );

        rval.setBeguenstigterZahlungsPflichtiger(
                row.getField("Beguenstigter/Zahlungspflichtiger")
        );

        rval.setKontonummer(
                row.getField("Kontonummer" )
        );

        rval.setBlz(
                row.getField("BLZ" )
        );

        rval.setBetrag(
                parseMoney(row.getField("Betrag" ))
        );

        rval.setWaehrung(
                row.getField("Waehrung" )
        );

        rval.setInfo(
                row.getField("Info" )
        );


        return rval;
    }

    private static Double parseMoney(String betrag) {
        try {
            DecimalFormat parser = new DecimalFormat( "#.00" );
            parser.setMaximumFractionDigits(2);
            parser.setCurrency( Currency.getInstance( Locale.GERMANY ));
            parser.setNegativePrefix( "-" );
            parser.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance(Locale.GERMANY) );

            return parser.parse(betrag)
                    .doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static Date parseDate(String val) {
        try {
            return new SimpleDateFormat("dd.MM.yy" )
                    .parse(val);
        } catch (ParseException e) {
            e.printStackTrace();

            throw new RuntimeException( e );
        }
    }

}

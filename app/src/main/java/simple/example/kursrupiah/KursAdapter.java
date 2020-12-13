package simple.example.kursrupiah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class KursAdapter extends ArrayAdapter<NilaiKurs> {

    private static DecimalFormat formatUang;
    private Context context;


    public KursAdapter(@NonNull Context context, @NonNull List<NilaiKurs> objects) {
        super(context, R.layout.row_kurs, objects);
        this.context = context;
        siapkanFormatUang();
    }

    private static class ViewHolder {
        TextView txMataUang;
        TextView txNilaiKurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NilaiKurs kurs = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_kurs, parent, false);
            viewHolder.txMataUang = convertView.findViewById(R.id.row_text_mata_uang);
            viewHolder.txNilaiKurs = convertView.findViewById(R.id.row_text_nilai_kurs);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txMataUang.setText("1 "+kurs.getMataUang());
        String nilaiKurs = formatUang.format(kurs.getNilaiKurs());
        viewHolder.txNilaiKurs.setText(nilaiKurs+" IDR");
        return convertView;
    }

    private static void siapkanFormatUang() {
        formatUang = new DecimalFormat("#,###.00");
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setGroupingSeparator('.');
        s.setDecimalSeparator(',');
        formatUang.setDecimalFormatSymbols(s);
    }
}

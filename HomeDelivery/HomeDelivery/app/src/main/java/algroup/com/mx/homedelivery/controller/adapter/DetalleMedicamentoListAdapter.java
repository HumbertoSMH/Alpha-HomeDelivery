package algroup.com.mx.homedelivery.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Medicamento;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.utils.LogUtil;


/**
 * Created by MAMM on 20/04/15.
 */
public class DetalleMedicamentoListAdapter extends BaseAdapter {
    private static final String CLASSNAME = DetalleMedicamentoListAdapter.class.getSimpleName();

    private Medicamento[] medicamentos;
    private Context context;


    public DetalleMedicamentoListAdapter(Medicamento[] medicamentos, Context context) {
        LogUtil.printLog(CLASSNAME, ".DetalleMedicamentoListAdapter() medicamentos:" + medicamentos);
        this.medicamentos = medicamentos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return medicamentos.length;
    }

    @Override
    public Object getItem(int position) {
        return medicamentos[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_detalle_entrega_layout , parent,
                false );
        Medicamento itemMedicamento = this.medicamentos[ position ];

        TextView nombreMedicamentoTextView = (TextView) rowView.findViewById( R.id.nombreMedicamentoTextView );
        nombreMedicamentoTextView.setText( itemMedicamento.getNombreMedicamento() );

        TextView cantidadTextView = (TextView) rowView.findViewById( R.id.cantidadTextView );
        cantidadTextView.setText( "Cantidad:" + itemMedicamento.getCantidad() ) ;

        TextView loteTextView = (TextView) rowView.findViewById( R.id.loteTextView );
        loteTextView.setText( "Lote:" + itemMedicamento.getLote() );

        TextView caducidadTextView = (TextView) rowView.findViewById( R.id.caducidadTextView );
        caducidadTextView.setText( "Caducidad:" + itemMedicamento.getFechaCaducidad() );


        TextView posicionTextView = (TextView) rowView.findViewById( R.id.posicionTextView );
        posicionTextView.setText( "" + (position + 1) );


        return rowView;
    }


}

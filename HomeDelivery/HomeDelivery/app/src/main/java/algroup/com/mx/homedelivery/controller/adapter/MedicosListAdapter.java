package algroup.com.mx.homedelivery.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;
import algroup.com.mx.homedelivery.business.Visita;
import algroup.com.mx.homedelivery.utils.LogUtil;


/**
 * Created by MAMM on 20/04/15.
 */
public class MedicosListAdapter extends BaseAdapter {
    private static final String CLASSNAME = MedicosListAdapter.class.getSimpleName();

    private Visita[] visitas;
    private Context context;


    public MedicosListAdapter(Visita[] visitas, Context context) {
        LogUtil.printLog(CLASSNAME, ".MedicosListAdapter() visitas:" + visitas);
        this.visitas = visitas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return visitas.length;
    }

    @Override
    public Object getItem(int position) {
        return visitas[position];
    }

    @Override
    public long getItemId(int position) {
        return  Long.parseLong( visitas[position].getIdVisita() );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_medicos_layout , parent,
                false );
        Visita itemVisita = this.visitas[ position ];

        TextView medicoTextView = (TextView) rowView.findViewById( R.id.medicoTextView );
        medicoTextView.setText( itemVisita.getNombreMedico() );

        TextView especialidadTextView = (TextView) rowView.findViewById( R.id.especialidadTextView );
        especialidadTextView.setText( itemVisita.getEspecialidadMedico() + " - " + itemVisita.getPortafolio() ) ;

        TextView direccionTextView = (TextView) rowView.findViewById( R.id.direccionTextView );
        direccionTextView.setText( itemVisita.getDireccionMedico() );

        TextView estatusView = (TextView) rowView.findViewById( R.id.estatusView );
        estatusView.setText( itemVisita.getEstatusVisita().getNombreEstatus() );

        TextView coordenadasMedicoTextView = (TextView) rowView.findViewById( R.id.coordenadasMedicoTextView );
        coordenadasMedicoTextView.setText( itemVisita.getCoordenadasMedico() );

        LinearLayout celdaMedicoLinearLayout = (LinearLayout)rowView.findViewById( R.id.celdaMedicoLinearLayout );
        celdaMedicoLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() );
        celdaMedicoLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() - 50);

        return rowView;
    }

}

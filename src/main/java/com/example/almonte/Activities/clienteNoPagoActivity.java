package com.example.almonte.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.almonte.Fragments.rutinasDetailFragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.almonte.R;

import com.example.almonte.Activities.datos.DummyContent;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link pagoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class clienteNoPagoActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean tabletMode;
    //    private OnItemClickListener mListener;
    private Button btnPagar;
    private Button btnRendimiento;
    private Button btnDetalle;
    private List<DummyContent.DummyItem> mValues;
    private RecyclerView mRecyclerView;
    private AdapterClienteNoPagoActivity mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;

    /*
    private View.OnClickListener pagarClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) v.getTag();
            if (tabletMode) {
                Bundle arguments = new Bundle();
                arguments.putString(rutinasDetailFragment.ARG_ITEM_ID, item.id);
                rutinasDetailFragment fragment = new rutinasDetailFragment();
                fragment.setArguments(arguments);
                FragmentActivity mParentActivity = new FragmentActivity();
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);

                context.startActivity(intent);
            }
        }
    };

     */

    //  main Activity para la lista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_no_pago);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        btnPagar = (Button) findViewById(R.id.btnPagar);
        btnRendimiento = (Button) findViewById(R.id.btnRendimiento);
        btnDetalle = (Button) findViewById(R.id.btnVerCliente);

        buildRecyclerView();
       // setButton();

    } //...


    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.item_list);
        mLayoutmanager = new LinearLayoutManager(this);
        mAdapter = new AdapterClienteNoPagoActivity(this, DummyContent.ITEMS, tabletMode);

        mRecyclerView.setLayoutManager(mLayoutmanager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterClienteNoPagoActivity.OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Toast.makeText(getBaseContext(), "Item click" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPagarClick(View v,int position) {
                Toast.makeText(getBaseContext(), "pagar click" + position, Toast.LENGTH_SHORT).show();
                DummyContent.DummyItem item = (DummyContent.DummyItem) v.getTag();
                if (tabletMode) {
                    Bundle arguments = new Bundle();
                    arguments.putString(rutinasDetailFragment.ARG_ITEM_ID, item.id);
                    rutinasDetailFragment fragment = new rutinasDetailFragment();
                    fragment.setArguments(arguments);
                    FragmentActivity mParentActivity = new FragmentActivity();
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, pagoDetailActivity.class);
                    intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        });
    }

    public void setButton() {
        btnPagar = (Button) findViewById(R.id.btnPagar);
        btnRendimiento = (Button) findViewById(R.id.btnRendimiento);
        btnDetalle = (Button) findViewById(R.id.btnVerCliente);

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Pagar click con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }





/*
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            tabletMode = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

 */
/*
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final clienteNoPagoActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean tabletMode;

 */

    //click to access pagar activity
/*
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, tabletMode));
    }

      private View.OnClickListener btnPagar = new View.OnClickListener(){
            @Override
            public void onClick (View v){
            if (mListener != null) {
                int position = 0;
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onPagarClick(position);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, pagoDetailActivity.class);
                    // intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        }
        };



    SimpleItemRecyclerViewAdapter(clienteNoPagoActivity parent,
                                  List<DummyContent.DummyItem> items,
                                  boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        tabletMode = twoPane;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ClienteNoPago_list_content, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        mListener.onPagarClick(position);
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(pagarClick);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

//break
        //Adapter para la lista de clientes no pagos

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        Button btnPagar;

        ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.nombre_cliente);
            mContentView = (TextView) view.findViewById(R.id.apellido_cliente);
            btnPagar = (Button) view.findViewById(R.id.btnPagar);

                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(listener != null) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                                Context context = v.getContext();
                                Intent intent = new Intent(context, pagoDetailActivity.class);
                               // intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);

                                context.startActivity(intent);
                            }
                        }
                    }
                });

        }
    }
}


public interface OnItemClickListener {
    public void onItemClick(int position);

    public void onPagarClick(int position);
}
v
 */

    // }
}

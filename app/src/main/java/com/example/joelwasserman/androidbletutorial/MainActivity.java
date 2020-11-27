package com.example.joelwasserman.androidbletutorial;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joelwasserman.androidbletutorial.model.DataModel;
import com.example.joelwasserman.androidbletutorial.model.MyData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button startScanningButton;
    Button stopScanningButton;
    TextView peripheralTextView;
    //*******************************************************
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    //private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    //private  ArrayList<String> nameArray;
    // private  ArrayList<String> addressArray;
    // private  ArrayList<String> adressArray;
    //  private  ArrayList<String> adressArray;

    private static ArrayList<String> removedItems;
    private static ArrayList<DataModel> data;
    private static MyData myData;

    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myData.addressArray = new ArrayList<String>();
        myData.nameArray = new ArrayList<String>();
        myData.timeArray = new ArrayList<String>();
        myData.valeurArray = new ArrayList<String>();
        myData.timeArray = new ArrayList<String>();

        /********************************/
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);

        data = new ArrayList<DataModel>();


        removedItems = new ArrayList<String>();


        /*********************************************************/

        // peripheralTextView = (TextView) findViewById(R.id.PeripheralTextView);
        //peripheralTextView.setMovementMethod(new ScrollingMovementMethod());

        startScanningButton = (Button) findViewById(R.id.StartScanButton);
        startScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScanning();
            }
        });

        stopScanningButton = (Button) findViewById(R.id.StopScanButton);
        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopScanning();
            }
        });
        stopScanningButton.setVisibility(View.INVISIBLE);

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
    }

    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if (result.getDevice().getName() == null || result.getDevice().getName().isEmpty() || !result.getDevice().getName().startsWith("NXT")) {
                return;
            }
            Log.d("get name", result.getDevice().getName());
            //byte[] mScanRecord = result.getScanRecord().getBytes();
            myData.addressArray.add(result.getDevice().getAddress());
            myData.nameArray.add(result.getDevice().getName());
            myData.timeArray.add(result.getDevice().getName());
            myData.valeurArray.add(result.getScanRecord().getBytes().toString());
            for (int i =0; i< result.getScanRecord().getManufacturerSpecificData().size();i++){
                if( result.getScanRecord().getManufacturerSpecificData().get(i) == null)
                    break;
                Log.d("test2", result.getScanRecord().getManufacturerSpecificData().get(i).toString());
                //Log.d("test size", String.valueOf(result.getScanRecord().getManufacturerSpecificData().get(i).length()));
            }
            data.add(new DataModel(
                myData.nameArray.get(myData.nameArray.size()-1),
                myData.addressArray.get(myData.addressArray.size()-1),
                myData.timeArray.get(myData.timeArray.size()-1),
                myData.valeurArray.get(myData.valeurArray.size()-1)
            ));

            adapter = new CustomAdapter(data);
            recyclerView.setAdapter(adapter);

            // peripheralTextView.append("Device Name: " + result.getDevice().getName() + "\n"+ " data: " + result.getScanRecord().getBytes() + "\n");
            //SensorViewHolder holder ;
            //holder.nameSensor.setText(result.getDevice().getName());

            // Log.d("result 1: ", result.getScanRecord().getBytes(StandardCharsets.UTF_8).toString() + " name : " + result.getDevice().getName());
            //Log.d("result 2: ", result.getDevice().toString() + " name : " + result.getDevice().getName());
            //Log.d("result 3: ", result.toString() + " name : " + result.getDevice().getName());


            // auto scroll for text view
         /*  final int scrollAmount = peripheralTextView.getLayout().getLineTop(peripheralTextView.getLineCount()) - peripheralTextView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                peripheralTextView.scrollTo(0, scrollAmount);*/
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void startScanning() {
        System.out.println("start scanning");
        //peripheralTextView.setText("");
        recyclerView.setAdapter(null);
        startScanningButton.setVisibility(View.INVISIBLE);
        stopScanningButton.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(leScanCallback);
            }
        });
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
//        peripheralTextView.append("Stopped Scanning");
        startScanningButton.setVisibility(View.VISIBLE);
        stopScanningButton.setVisibility(View.INVISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });
    }

    /********************************************************************************************************/
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
           // removeItem(v);
            /************************************/
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.nameSensor);

            String selectedName = (String) textViewName.getText();
            String selectedItemId = "";

            for (int i = 0; i < myData.nameArray.size(); i++) {
                if (selectedName.equals(myData.nameArray.get(i))) {
                    selectedItemId = myData.valeurArray.get(i);
                }
            }
            Intent intent = new Intent(context,LevelActivity.class);
            intent.putExtra("valueTemperature",selectedItemId );
            context.startActivity(intent);

        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.nameSensor);

            String selectedName = (String) textViewName.getText();
            String selectedItemId = "";


            for (int i = 0; i < myData.nameArray.size(); i++) {
                if (selectedName.equals(myData.nameArray.get(i))) {
                    selectedItemId = myData.addressArray.get(i);
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
            //check if any items to add
            if (removedItems.size() != 0) {
                addRemovedItemToList();
            } else {
                Toast.makeText(this, "Nothing to add", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    private void addRemovedItemToList() {
        int addItemAtListPosition = 3;

        data.add(addItemAtListPosition, new DataModel(
                myData.nameArray.get(getIndex(myData.nameArray, removedItems.get(0))),
                myData.valeurArray.get(getIndex(myData.valeurArray, removedItems.get(0))),
                myData.addressArray.get(getIndex(myData.addressArray, removedItems.get(0))),
                myData.timeArray.get(getIndex(myData.timeArray, removedItems.get(0)))
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }

    public int getIndex(ArrayList<String> mdata, String s) {
        int result = 0;
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).equals(s))
                result = i;
        }

        return result;
    }
}


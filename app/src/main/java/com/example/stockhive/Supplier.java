//package com.example.stockhive;
//
//public class Supplier extends AppCompatActivity {
//
//    private FirebaseDatabase firebaseDatabase;
//    private DatabaseReference reference;
//
//    EditText itemET, quantityET;
//    RecyclerView itemsRV;
//    SupplierAdapter adapter;
//    ArrayList<Item> itemList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_supplier);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        itemList = new ArrayList<Item>();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        reference = firebaseDatabase.getReference();
//        itemET = findViewById(R.id.edt_item);
//        quantityET = findViewById(R.id.edt_quantity);
//        itemsRV = findViewById(R.id.rv_view);
//        adapter = new SupplierAdapter(itemList);
//        itemsRV.setAdapter(adapter);
//        itemsRV.setLayoutManager(new LinearLayoutManager(this));
//
//
//        reference.child("items").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                itemList.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Item item = postSnapshot.getValue(Item.class);
//
//                    if (item != null) {
//                        item.setId(postSnapshot.getKey());
//                        itemList.add(item);
//                    }
//
//                }
//                adapter.setData(itemList);
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//
//    public void addItem (View v){
//        String id = reference.push().getKey();
//        Item item = new Item(itemET.getText().toString(),
//                Integer.parseInt(quantityET.getText().toString()));
//
//        item.setId(id);
//
//        reference.child("items").child(id).setValue(item);
//        Toast.makeText(this, "added new item",
//                Toast.LENGTH_LONG).show();
//
//    }
//
//
//    public void deleteItems(View v){
//        reference.child("items").removeValue()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        Toast.makeText(this, "All Items Deleted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(this, "Failed to Delete Items", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//
//
//
//
//    public void logoutsup(View view) {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        finish();
//    }
//}

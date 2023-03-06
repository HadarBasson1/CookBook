package com.example.cookbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.model.Recipe;

import java.util.List;

class RecipeViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView idTv;
    CheckBox cb;
    List<Recipe> data;
    ImageView avatarImage;
    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener, List<Recipe> data) {
        super(itemView);
        this.data = data;
//        nameTv = itemView.findViewById(R.id.studentlistrow_name_tv);
//       idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
//        avatarImage = itemView.findViewById(R.id.studentlistrow_avatar_img);
//       cb = itemView.findViewById(R.id.studentlistrow_cb);
       cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int pos = (int)cb.getTag();
//                Student st = data.get(pos);
//                st.cb = cb.isChecked();
            }
       });
        itemView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
   }

    public void bind(Recipe recipe, int pos) {
//        nameTv.setText(st.name);
//        idTv.setText(st.id);
//       cb.setChecked(st.cb);
//        cb.setTag(pos);
//        if (st.getAvatarUrl()  != null && st.getAvatarUrl().length() > 5) {
//            Picasso.get().load(st.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImage);
//        }else{
//           avatarImage.setImageResource(R.drawable.avatar);
//        }
    }

}

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    OnItemClickListener listener;

public static interface OnItemClickListener{
    void onItemClick(int pos);
}

    LayoutInflater inflater;
    List<Recipe> data;
    public void setData(List<Recipe> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public RecipeRecyclerAdapter(LayoutInflater inflater, List<Recipe> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.student_list_row,parent,false);
//        return new RecipeViewHolder(view,listener, data);

         return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        holder.bind(recipe,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}
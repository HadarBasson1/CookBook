package com.example.cookbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

class RecipeViewHolder extends RecyclerView.ViewHolder{
    TextView user_name;
    TextView title;
    ImageView avatar_recipe;
    ImageView avatar_user;
    List<Recipe> data;
//    ImageView avatarImage;
    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener, List<Recipe> data) {
        super(itemView);
        this.data = data;

        user_name = itemView.findViewById(R.id.recipe_card_row_user_name);
        title = itemView.findViewById(R.id.recipe_card_row_title);
        avatar_recipe = itemView.findViewById(R.id.recipe_card_row_img);
        avatar_user = itemView.findViewById(R.id.home_user_img);

//       cb = itemView.findViewById(R.id.studentlistrow_cb);
//       cb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                int pos = (int)cb.getTag();
////                Student st = data.get(pos);
////                st.cb = cb.isChecked();
//            }
//       });
        itemView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
   }

    public void bind(Recipe recipe, int pos) {

        title.setText(recipe.title);
        user_name.setText(recipe.editor);
//       cb.setChecked(st.cb);
//        cb.setTag(pos);
       if (recipe.getImgUrl()  != null && recipe.getImgUrl().length() > 5) {
           Picasso.get().load(recipe.getImgUrl()).placeholder(R.drawable.salmon).into(avatar_recipe);
      }else{
           avatar_recipe.setImageResource(R.drawable.salmon);
       }
    }

}

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    OnItemClickListener listener;

public static interface OnItemClickListener{
    void onItemClick(int pos);
}

    LayoutInflater inflater;
    List<Recipe> data;
    List<User> users;
    public void setData(List<Recipe> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setUsers(List<User> users){
        this.users = users;
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
        View view = inflater.inflate(R.layout.recipe_card_for_list,parent,false);
        return new RecipeViewHolder(view,listener, data);
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

package comp3350.quizrus.presentation.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.quizrus.R;

public class QuizRecycleViewAdapter extends RecyclerView.Adapter<QuizRecycleViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> quizTitles;

    //quizTitles passed in from QuizSelectionActivity
    public QuizRecycleViewAdapter(Context context, ArrayList<String> quizTitles){
        this.context = context;
        this.quizTitles = quizTitles;
    }

    @NonNull
    @Override
    public QuizRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_quiz_selection_row, parent, false);
        return new QuizRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizRecycleViewAdapter.MyViewHolder holder, int position) {
        //Changing the values of the recycle view items (implementing the quiz tiles string to each item)
        //The "position" from the parameter where the user is scrolled to, we can change the title accordingly

        TextView itemTextView = holder.quizTitle;

        //Changing the name of the item according to the position
        itemTextView.setText(quizTitles.get(position));

        //set the quiz item fade animation when touched
        holder.itemView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    itemTextView.setAlpha(0.9f); // Fade when pressed
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    itemTextView.setAlpha(1.0f); // Restore when released
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    //Number of items in the recycle view (rows of selectable quiz)
    //Defined by the size of the quizTitles arraylist
    public int getItemCount() {
        return quizTitles.size();
    }


    //==============================ViewHolder Class==========================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView quizTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizTitle = itemView.findViewById(R.id.quiz_selection_item_title);
        }

    }
}

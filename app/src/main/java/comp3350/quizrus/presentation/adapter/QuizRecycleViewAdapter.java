package comp3350.quizrus.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.presentation.PreviewActivity;

public class QuizRecycleViewAdapter extends RecyclerView.Adapter<QuizRecycleViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<Quiz> quizzes;

    // quizTitles passed in from QuizSelectionActivity
    public QuizRecycleViewAdapter(Context context, List<Quiz> quizzes) {
        this.context = context;
        this.quizzes = quizzes;
    }

    @NonNull
    @Override
    public QuizRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_quiz_selection_row, parent, false);
        return new QuizRecycleViewAdapter.MyViewHolder(view);
    }

    // Changing the values of the recycle view items (implementing the quiz tiles
    // string to each item)
    // The "position" from the parameter where the user is scrolled to, we can
    // change the title accordingly
    @Override
    public void onBindViewHolder(@NonNull QuizRecycleViewAdapter.MyViewHolder holder, int position) {
        TextView itemTextView = holder.quizTitle;
        Quiz currQuiz = quizzes.get(position);

        // Changing the name of the item according to the position
        itemTextView.setText(currQuiz.getTitle());

        // set the quiz item fade animation when touched
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    itemTextView.setAlpha(0.9f); // Fade when pressed
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    itemTextView.setAlpha(1.0f); // Restore when released
                }
                return false;
            }
        });

        // start new activity page when the quiz item is pressed
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PreviewActivity.class);
            intent.putExtra("currQuiz", currQuiz); // pass the Quiz object that is pressed
            context.startActivity(intent);
        });
    }

    @Override
    // Number of items in the recycle view (rows of selectable quiz)
    // Defined by the size of the quizTitles arraylist
    public int getItemCount() {
        return quizzes.size();
    }

    // ==============================ViewHolder
    // Class==========================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView quizTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizTitle = itemView.findViewById(R.id.quiz_selection_item_title);
        }

    }
}

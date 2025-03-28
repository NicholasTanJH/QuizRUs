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
import comp3350.quizrus.objects.User;
import comp3350.quizrus.presentation.PreviewActivity;

public class QuizRecycleViewAdapter extends RecyclerView.Adapter<QuizRecycleViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<Quiz> quizzes;
    private final User currUser;

    /**
     * quizTitles passed in from QuizSelectionActivity
     * 
     * @param context
     *                 This is the context for the android resource.
     * @param quizzes
     *                 This is the list of quizzes available.
     * @param currUser
     *                 This is the currently logged in user.
     */
    public QuizRecycleViewAdapter(Context context, List<Quiz> quizzes, User currUser) {
        this.context = context;
        this.quizzes = quizzes;
        this.currUser = currUser;
    }

    /**
     * @param parent
     *                 The ViewGroup into which the new View will be added after it
     *                 is bound to an adapter position.
     * @param viewType
     *                 The view type of the new View.
     * @return
     *         Returns the quiz recycle view
     */
    @NonNull
    @Override
    public QuizRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_quiz_selection_row, parent, false);
        return new QuizRecycleViewAdapter.MyViewHolder(view);
    }

    /**
     * @param holder
     *                 The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position
     *                 The position of the item within the adapter's data set.
     */
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
            intent.putExtra("currUser", currUser);
            context.startActivity(intent);
        });
    }

    /**
     * @return
     *         Number of items in the recycle view (rows of selectable quiz)
     *         Defined by the size of the quizTitles arraylist
     */
    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    // ==============================ViewHolder
    // Class============================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView quizTitle;

        /**
         * @param itemView
         *                 This grabs the quiz title view
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizTitle = itemView.findViewById(R.id.quiz_selection_item_title);
        }

    }
}

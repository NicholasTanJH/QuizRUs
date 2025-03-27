package comp3350.quizrus.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.objects.UserQuizScore;

public class LeaderboardRecycleViewAdapter extends RecyclerView.Adapter<LeaderboardRecycleViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<UserQuizScore> userQuizScoreList;
    private final int totalQuestionNumber;
    private final boolean isEmptyLeaderboard;

    /**
     * @param context
     * This is the context for the android resources.
     * @param userQuizScoreList
     * The list of the user's and their scores.
     * @param totalQuestionNumber
     * The total number of questions.
     */
    public LeaderboardRecycleViewAdapter(Context context, List<UserQuizScore> userQuizScoreList, int totalQuestionNumber) {
        this.context = context;
        this.userQuizScoreList = userQuizScoreList;
        this.totalQuestionNumber = totalQuestionNumber;
        isEmptyLeaderboard = userQuizScoreList.isEmpty();
    }

    /**
     * @param parent
     * The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType
     * The view type of the new View.
     * @return
     * Returns the view for the leaderboard.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_leaderboard_row, parent, false);
        return new LeaderboardRecycleViewAdapter.MyViewHolder(view);
    }

    /**
     * This binds the fragmented layout to the leaderboard, and inputs the information needed.
     * @param holder
     * The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position
     * The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView leaderboardOrderNumberTV = holder.leaderboardOrderNumberTV;
        TextView leaderboardNameTV = holder.leaderboardNameTV;
        TextView leaderboardScoreTV = holder.leaderboardScoreTV;
        TextView leaderboardScoreInfoTV = holder.leaderboardScoreInfoTV;

        //show a default leaderboard item if the leaderboard is empty
        if (isEmptyLeaderboard) {
            leaderboardNameTV.setText(R.string.this_quiz_hasn_t_been_attempted_yet);
        } else {
            int currOrderNumber = position + 1;
            UserQuizScore currUserQuizScore = userQuizScoreList.get(position);

            String currName = currUserQuizScore.getUser().getUsername();
            int currScore = currUserQuizScore.getScore();
            int currNumCorrect = currUserQuizScore.getNumCorrect();
            int currTimeTaken = currUserQuizScore.getTimeTaken();

            // Changing the name of the item according to the position
            leaderboardOrderNumberTV.setText(currOrderNumber + ".");
            leaderboardNameTV.setText(currName);
            leaderboardScoreTV.setText(Integer.toString(currScore));
            leaderboardScoreInfoTV.setText(currNumCorrect + "/" + totalQuestionNumber + " in " + currTimeTaken + "s");

            // Set background color based on order number
            if (currOrderNumber == 1) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_first); // Gold background for 1st place
            } else if (currOrderNumber == 2) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_second); // Silver background for 2nd place
            } else if (currOrderNumber == 3) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_third); // Bronze background for 3rd place
            }
        }
    }

    /**
     * @return
     * Gets the number of leaderboard entries
     */
    @Override
    public int getItemCount() {
        if (isEmptyLeaderboard) {
            return 1;
        } else {
            return userQuizScoreList.size();
        }
    }

    // ==============================ViewHolder
    // Class==========================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaderboardOrderNumberTV;
        TextView leaderboardNameTV;
        TextView leaderboardScoreTV;
        TextView leaderboardScoreInfoTV;

        /**
         * @param itemView
         * This is the text in the text views for the leaderboard
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderboardOrderNumberTV = itemView.findViewById(R.id.leaderboardOrderNumberTV);
            leaderboardNameTV = itemView.findViewById(R.id.leaderboardNameTV);
            leaderboardScoreTV = itemView.findViewById(R.id.leaderboardScoreTV);
            leaderboardScoreInfoTV = itemView.findViewById(R.id.leaderboardScoreInfoTV);
        }

    }
}

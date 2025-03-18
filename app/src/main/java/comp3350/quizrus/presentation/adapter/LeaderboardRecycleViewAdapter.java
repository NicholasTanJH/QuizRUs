package comp3350.quizrus.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.quizrus.R;

public class LeaderboardRecycleViewAdapter extends RecyclerView.Adapter<LeaderboardRecycleViewAdapter.MyViewHolder> {
    private final Context context;
    private final String[] leaderboardNames;
    private final int[] leaderboardScores;
    private final int[] leaderboardCorrects;
    private final int[] leaderboardTimes;
    private final int totalQuestionNumber;
    private boolean isEmptyLeaderboard;

    public LeaderboardRecycleViewAdapter(Context context, String[] leaderboardNames, int[] leaderboardScore, int[] leaderboardCorrects, int[] leaderboardTimes, int totalQuestionNumber) {
        this.context = context;
        this.leaderboardNames = leaderboardNames;
        this.leaderboardScores = leaderboardScore;
        this.leaderboardCorrects = leaderboardCorrects;
        this.leaderboardTimes = leaderboardTimes;
        this.totalQuestionNumber = totalQuestionNumber;
        isEmptyLeaderboard = (leaderboardNames == null || leaderboardNames.length == 0);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_leaderboard_row, parent, false);
        return new LeaderboardRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView leaderboardOrderNumberTV = holder.leaderboardOrderNumberTV;
        TextView leaderboardNameTV = holder.leaderboardNameTV;
        TextView leaderboardScoreTV = holder.leaderboardScoreTV;
        TextView leaderboardCorrectTV = holder.leaderboardCorrectTV;
        TextView leaderboardTimeTV = holder.leaderboardTimeTV;


        //show a default leaderboard item if the leaderboard is empty
        if(isEmptyLeaderboard){
            leaderboardNameTV.setText("This quiz hasn’t been attempted yet");
        }else{
            int currOrderNumber = position + 1;
            String currName = leaderboardNames[position];
            int currScore = leaderboardScores[position];
            int currCorrect = leaderboardCorrects[position];
            int currTime = leaderboardTimes[position];

            // Changing the name of the item according to the position
            leaderboardOrderNumberTV.setText(currOrderNumber + ".");
            leaderboardNameTV.setText(currName);
            leaderboardScoreTV.setText(Integer.toString(currScore));
            leaderboardCorrectTV.setText(Integer.toString(currCorrect) + "/" + totalQuestionNumber);
            leaderboardTimeTV.setText(Integer.toString(currTime) + "s");

            // Set background color based on order number
            if(currOrderNumber == 1) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_first); // Gold background for 1st place
            } else if(currOrderNumber == 2) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_second); // Silver background for 2nd place
            } else if(currOrderNumber == 3) {
                holder.itemView.setBackgroundResource(R.drawable.leaderboard_third); // Bronze background for 3rd place
            }
        }
    }

    @Override
    public int getItemCount() {
        if(isEmptyLeaderboard){
            return 1;
        }else{
            return leaderboardNames.length;
        }
    }

    // ==============================ViewHolder
    // Class==========================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaderboardOrderNumberTV;
        TextView leaderboardNameTV;
        TextView leaderboardScoreTV;
        TextView leaderboardCorrectTV;
        TextView leaderboardTimeTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderboardOrderNumberTV = itemView.findViewById(R.id.leaderboardOrderNumberTV);
            leaderboardNameTV = itemView.findViewById(R.id.leaderboardNameTV);
            leaderboardScoreTV = itemView.findViewById(R.id.leaderboardScoreTV);
            leaderboardCorrectTV = itemView.findViewById(R.id.leaderboardCorrectTV);
            leaderboardTimeTV = itemView.findViewById(R.id.leaderboardTimeTV);
        }

    }
}

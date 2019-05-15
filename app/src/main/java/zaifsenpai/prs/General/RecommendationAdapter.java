package zaifsenpai.prs.General;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zaifsenpai.prs.R;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private Context context;
    private List<Recommendation> list;

    public RecommendationAdapter(Context context, List<Recommendation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recommendation_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ImageLoader imageLoader = ImageLoader.getInstance();

        Recommendation recommendation = list.get(i);

        viewHolder.RecommendationText.setText(recommendation.Name);
        imageLoader.displayImage(recommendation.Image, viewHolder.RecommendationImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView RecommendationImage;
        public TextView RecommendationText;

        public ViewHolder(View itemView) {
            super(itemView);

            RecommendationImage = itemView.findViewById(R.id.recommendation_image);
            RecommendationText = itemView.findViewById(R.id.recommendation_text);
        }
    }
}


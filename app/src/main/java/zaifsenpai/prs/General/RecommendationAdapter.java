package zaifsenpai.prs.General;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        final Recommendation recommendation = list.get(i);

        View.OnClickListener listerner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recommendation.Url));
                context.startActivity(browserIntent);
            }
        };

        viewHolder.RecommendationText.setText(recommendation.Name);
        imageLoader.displayImage(recommendation.Image, viewHolder.RecommendationImage);
        viewHolder.RecommendationPrice.setText(recommendation.Price);

        viewHolder.RecommendationText.setOnClickListener(listerner);
        viewHolder.RecommendationPrice.setOnClickListener(listerner);
        viewHolder.RecommendationImage.setOnClickListener(listerner);
        viewHolder.RecommendationLayout.setOnClickListener(listerner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView RecommendationImage;
        TextView RecommendationText;
        TextView RecommendationPrice;
        LinearLayout RecommendationLayout;

        ViewHolder(View itemView) {
            super(itemView);

            RecommendationImage = itemView.findViewById(R.id.recommendation_image);
            RecommendationText = itemView.findViewById(R.id.recommendation_text);
            RecommendationPrice = itemView.findViewById(R.id.recommendation_price);
            RecommendationLayout = itemView.findViewById(R.id.recommendationLayout);
        }
    }
}

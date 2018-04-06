package club.playerfox.fragmentpractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.playerfox.fragmentpractice.News;
import club.playerfox.fragmentpractice.NewsContentActivity;
import club.playerfox.fragmentpractice.R;

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.news_recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setTitle("hhhhxxx");
            news.setContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxz");
            newsList.add(news);
        }
        return newsList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_fragment) != null){
            isTwoPane = true;
        }else {
            isTwoPane = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private List<News> newsList;

        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item,parent,false);
            ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(v -> {
                News news = newsList.get(holder.getAdapterPosition());
                if (isTwoPane){
                    NewsContentFragment fragment = (NewsContentFragment) getFragmentManager()
                            .findFragmentById(R.id.news_content_fragment);
                    fragment.refresh(news.getTitle(),news.getContent());
                }else {
                    NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        public NewsAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            public ViewHolder(View itemView) {
                super(itemView);
                newsTitleText = itemView.findViewById(R.id.news_title);
            }
        }
    }
}


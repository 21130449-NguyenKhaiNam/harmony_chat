package com.example.harmony_chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private CardView avatarCardView;
    private ImageView avatarImageView;
    private ImageView find;
    private TextView setting, profile;

    private Button allButton;
    private Button unreadButton;
    private Button readButton;
    private Button pinnedButton;
    private Button requestButton;
    private List<Button> buttons;

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();
        boolean isSearchVisible = false;

        avatarCardView = findViewById(R.id.user_avatar);
        avatarImageView = findViewById(R.id.avatar_image_view);
        avatarCardView.setOnClickListener(e -> createPopUpWindow());

        find = findViewById(R.id.search_icon);
        find.setVisibility(isSearchVisible ? View.GONE : View.VISIBLE);
        find.setOnClickListener(v -> gotoSearchUser());

        // Liên kết các nút với mã Java
        allButton = findViewById(R.id.all_button);
        unreadButton = findViewById(R.id.unread_button);
        readButton = findViewById(R.id.read_button);
        pinnedButton = findViewById(R.id.pinned_button);
        requestButton = findViewById(R.id.request_button);

        // Thêm các nút vào danh sách
        buttons = Arrays.asList(allButton, unreadButton, readButton, pinnedButton, requestButton);

        // Đặt sự kiện nhấp chuột cho các nút
        allButton.setOnClickListener(view -> setSelectedButton(allButton));
        unreadButton.setOnClickListener(view -> setSelectedButton(unreadButton));
        readButton.setOnClickListener(view -> setSelectedButton(readButton));
        pinnedButton.setOnClickListener(view -> setSelectedButton(pinnedButton));
        requestButton.setOnClickListener(view -> setSelectedButton(requestButton));

        // Initialize RecyclerView
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatItemList = new ArrayList<>();
        chatItemList.add(new ChatItem("User1","Hello","https://www.tvtime.com/_next/image?url=https%3A%2F%2Fartworks.thetvdb.com%2Fbanners%2Fperson%2F8010329%2F62d46047aebd3.jpg&w=640&q=75","5:10"));
        chatItemList.add(new ChatItem("User2","Hi","data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBYWFRgVFRYYGRgaGBgaHBwYGBgYGhkZGBgZGRoYGBgcIS4lHB4rHxgYJjgmKy8xNTU1GiQ7QDszPy40NTEBDAwMEA8QHxISHjQrJSs0NDQ0NDQ0NDQ0MTQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NP/AABEIAQoAvgMBIgACEQEDEQH/xAAcAAAABwEBAAAAAAAAAAAAAAAAAQIDBAUGBwj/xABBEAACAQIDBAcGBAQFAwUAAAABAgADEQQhMQUSQVEGImFxgZGxEzKhwdHwB0JS4RRicvEjgqKywiRzkhUzQ1Nj/8QAGQEAAwEBAQAAAAAAAAAAAAAAAAEDAgQF/8QAKBEAAgICAgICAQMFAAAAAAAAAAECEQMhEjEiQVGBYQRxsRMjMjNC/9oADAMBAAIRAxEAPwDdMIhljjQpc46GtyJNOPRSx2KrIb0oqhStJe7FokTkJQEIsehhYCI7NKNDTiRaqXkwxthGnROULI2Hp2MuMPK8ALmxA7zFJtOmurjvGkhlkm+zqwYpRXRcXjbmVrbapsLob52yudNfWR8XthUF7m/I5j6yVpHRxkWFeU2PWN0uktJzYkD75HXwi8TUDDeUgg8RL4mmzmzxkltFcacbejJKxxlnS4nEmU9ehIRuJeVklVXSSlEopaGRViWrRDJElJPiaQ8K0S7xu0UtMkQ4GWzfO8YarEVakg1a8o2kJyZYCvFrWlMcRAuKkZTRuMi/SpHkeUVPFSSmLjTsfMt96DelcuKkmjcylpLY4tydIcKyl2tttaYO51jpfhfskvbW0UpLuu6Lz3jw5kaznm1OktG53G3zpcg/6RoBOaeRy0uj0MWGMVyl2PY/aTubk37za17932JXUsXZ1vvEFrbquM79/u317pTYraDNo5yOViRfwEstgYgKwLjfJGVgGbwJyGgzPKZSo05W9GuxO2RTphKabgA/Nl888+6ZbG7Wqbx3zYHhex8LH1k/au0lORJXv3SfEX9ZlsQQ17Hy0PgdPDKNJNik2kP18aW0JYX46+I1J7vKWGyNvOmQckfpY3Fu/iO/TmJn7W/MfEad/ZH1W+YI3vX6H1+M3XwYt+zqOytorVFxkw1HzEsAZzHY2PZH3lJuvvDs/UOz6zouGxQdA44j7E6MeS9Ps4c+Dj5R6HKsg1Ekt2kOqZqUiESI9OIKSQRI9Q2k2zaYkrH6Si0hGpHadTKbxu3RjJaVlzXqsdBIxD8pfHDiF7AcpGUbLcChZG5RBR+U0PsRyhGiOUzwQcDPoH5SSm/yluKA5RxMOOU0lQ/6VkHBUnJ+9e3sj+09qCgjBSC4HWY6Lf53Iy7e2P4pyCtJPfe+eoQDNmt2epHOcw6bbfBqHD0SRTQ9ZtS78Tfib317ecjKXKXFHdgwLGuUiHt7aW+9yTcm+dr3585RAFjz8hLPBYJ6trJl558yeJmkwfRh7aWJ58JKWRR0jpjicnb6MnQ2a7cPT1lnToMvujTLI29RNlQ2Huj58/v6RutsgkE2zy+Ak3ll8FVgijneNLkm/wA5CDkGa7a2xmAJ3TfP7voZlsRTZSQQQZXHkshlwtbQ+jbwzz9fOLpryNx5EdhkDDV7GSmex3l++YPdrLogTlY5Mnvr8R98Jq9g7QFgb9Rsv6Ty8PQnlMdTcN1lyPEcO8cpZ7Pr7jZjqPkw5N+oGD+UPTVPpnRjSNpHfDNJPR/Fe0p2PvJ1T3flPl6Sz9kJZNPZwyxcXRQ/whjVXAsRNJ7IRLU5u0JYzItsxoE2ewE1PshEVKYA04xppPQ5QTVMsigiSggdYQQmTKBbgg3BFew7YlqZiCg1QRdwM/jGkpnPlFgZi+QGefqZPJKkXxRtlJ0k2h/DYepWJ/xHG4g5a29ST5TjOFpF3Qal3Pjpr5mar8RNte1q7inqpcDv4mV3QLD+0xVIHRN9z4AAfEic8dJyOuW5KJ1XYGxFpUxl1rC/fLP+HAkikLCB2k+KNcnZEekIxUpyW5kaq0KNxbK3E0AbiZXbewVYEqLHs49k2FSMPTvrFRqzi+0MKUYgiJw9Xgf7EaGbbpRse4ZlGeuXETn5uptOnHK1s480eLtdMnBirdknYesVz1XiNbdtvvjKtKlxY/2kvDPw+yOU2yaN70X2iEdSbWPVa3I6E9x9Z0C4nGNlYko+7wOQ7RxUzq2ysTv01bjax8I4OnQZVaUiyuIliIkCFuyxzANo3VIt4wezialPLxjQNkoNeOLI6NHPaSDkUVD5aNM8jvWMUj840waHHHK9+82vM90q2x7GkeDHLPhNCaoAvOO9O9rGrVKA9VSfE3zksu2o/J04dJyfozGMrl2JPGbH8KlviX/7f/ITDTefhcje2qFRpTAvyJcfIGKaqNIMcm52zsASNuJUYg4kZqUt2k39IzSxdYGz7pHMH5SDaOhRZatGHWGX6t5UY/EuTuoczE2aSZMqADiIwai3tvDzlE2zHILVa5Ci98wB5nKIpYCnvWStvHsZSfMRpmi2x9DeB7jOV9JNm7jkgZHP6zq9KkQLFr98o9t7NDowtwMd07RlxUo0zlAMkUa9tY1Xp7rEcjGzOo4Ho0GEqh8jrwI7Ozn2zofQ/aIN0J/uPScfSqVMvdlbYdGDqcxbXXLkePcYdOx3apncEbL77oCTK3o9tRcRS311vmO3iPP5S2vLxaaOaUWmMbxhsDHbwOcvGDYKI4+FIkdqRlyySNUSc12UcUtlZ7GGEtJbCNul5uKofaIeOchG7pwbalTedu+d52hT/wAN+PVOncZwTFC7sbWFzl3mYl/n9HRFf2vsiCdP/CVLjEMBchafq+XwnMtz77p1H8GWBXFDiDR8iKnzvFPcRYtSL84XEu7tWqOi7p9mlK1r2Nt9gpIztkO3MzNbEfHioFrqShP5yhZeWaWuO8TpWIwu9xI7pHp7OQG5zPbOa3VHYqvlf16BRp9TPW0olT/EtzyvymoqpZTM5iKfWvBocX2Z/aHR9q9OoathUZh7ItZ1p01Pu7hyBI1YZ5+EqML0SCCzON+4syLukWvl8fgJ0Gkgdc9eNjCOz0Ge753PrC3VXoFGN21sqdmYV0WzVC47Rn5yXi6V1kkpY5CIr6TSWhvs45tPAt7V90aMb+srHoEazf18EXNYIQrM26CdAPzHyJmb6Q4IUVK3uSF63FjplyEcMrviSyYI05fZnbQ0JGYhLDUzpOE334c7WIrtTOlRL9zoNfFfSdUW04Z0SYriaLD9YB9PnO6KuQmofATWkwERNTTxi92N1Uy8ZQmWxqRlzHBEPOOEtlZR0RGOcWRGm1jynKdZCPWyNivcf+lvQzz7Wzdrabx9dZ33a9TcoVX5I58d02nBsNTJYki/z7JzzaUr/B244twr8iKyhE/mb4DU+oHnNh+D+NCYupSP/wAlK4/qptcD/wAWfymIxtXebuy+Nz8SZN6MY32GLoVb2CVFLH+Ruo/+lmhXjsy35aPRzMLSur4uzBRqZJrNkZDwFG7Fjrw7BOZuzriklbLCqOpnM9ilvcXl1iqLENZuGVxe0yuI2XV3t41CB/L+4jcq9DglXYjC45qVQq2Yvn3TQVagK3BuDpM82EtcnMnUmOYN3HU1U6dhi6N6ssRUEbrG4kNXPGOu9lvNcjLRj8TtijRqutViNWyDHU24DsmS6S7YXEOoQHdW+ZyLHu4CNdJqm9iHP3qZVSmPFFeXs582aTuPoEW0RFcJY5S56O192qhOm8p8QfsTv+FAZFYEEEXBnnHZ9XdcHtndOi+LtSTihGvLsI+f2VGXGRtx5R/Y0QpRFWnl4yVTIIyh1KeXjK8iVDaiIcSYqRqqkkoJG+RWMM4+iR0ULmSVoSvIwomH/EXGezwu4vv1GCj+kZse7TznKqtkpluJ6q/Mzd9P39pWzI3VG4mmQGbtfhc2H+XjOb7WxQd7KeqvVXw4+s5L5zZ2/wCvGvkgmKTX4QlWOU0vLHOuzuP4f7aGKwiBjepSApvfU7o6jf5lA8Q3KXjYQlgVZlsCMtPI5ThvRzbT4OsKq5iwV0vYOmVx3jUHn3md02TtGniKa1qTBkYZHiDxVhwYaETmlHZ1Rk0irxVfEobbyt2W3cuYPH4TO7a2tiSN1d1AONxcnwvlN/iaQYWImaxmxQ15KUZf8s9DBnw15xV/hGLotiqvVFZgOaqMueZEv9k4L2BLF2diLEuxYnjlfTOT0we4LRusMpqKaWyWXKpPxVIaL31kbaGKshA10HfG6ta0jlCxufCYlKjCRzvpCm7W/wAo+cq5oel9Gzq3O4+cz07MLuCPPzKpsKKEK0AlCQ4jWM6Z+HW31VhQc5HQ/fH6zmAMm4PEFCGBsQbg9szJG4yrR6YVCmYzTs4dvd990pswLTG9A+la10CObOMs/n3/AFmpxN1F190nTWxzztwmk7QpKnRKQxNQSJhsSGGUkhCYzAEi6z2Uka2NvKEuHI4yp29jzTptYgEjX9N9MuJmJSSRSEW3RxrplizvsgJL/mzyQa7oPMnMzIrSN7fYE0W1KLPdwp3CzdY5lyDmb8czr3ytoqu4xObsyqv8o1Y+OQHdJQkktHTkg5PfQ3Tw/VJMUQMh2+sfqWXLgoz77fvIiNdr+ME21Y3GMWkHXyPh6D9pd9Adu1sPikSmC6VXVGp87m28vJlGd+IFj2Z+q9yTLbolikou9d3VGSmQm9ck1H6t1UZkKLknu5zaXjTJSl5aPQZcSBiagkhAHRWByKggjLIi4MrsVswtpUYeAM5+R0RSK3F4oXteVGLxV8hJuO2KeDnyErUw26bHMybkyqS7G6dO5zkgiOqkS4maCzH9L8PdC36SDMSJ0rpBSvScfymc2InV+nfi0cf6leSYILQxBadBzCY5SaHRoM53VBZjeygXLWzso4nXKEiEqWGgtfsvpAaL3YuJak4dWK2zDi/V7HA1E7VsfpAKtMe1tTcWuSbI11BDKwNiCCCM+etrzh2w8eaVRHAvusDY6GxvY9h0nTxsuyivgRTejUsfYuWC02IuTTZc1GoK6Zi2kx0ylpotuitRizoWJKORnnlqPWbfCrMtsqkFd2A943M01OrYTXokgYuqRkNbXJ/SOc5xt3FHE1BTS/sxqeY4kntzl70m2y2VCiL1Kht2KvNuwDM/vKbF1Uw1IqGuxyJ4kmcmWV6O7BjpWyg6QUk3FGQFjYDgo+zME9bdYW4G8021sfvKxLWuLADgCeEyDtnDBG7ZvNLjFL2PVqnVtzNz28o1fKIJi2GU6ao5bbdjJjuAoF3VR+ZgvmbRlzNN0F2f7WqLC5Rka3ZfP0hK1FtCjXJJndcMlkVeSgeQtDcQ0OQgYzkOn2VuMpzO4ml1prKyymxNLOKikXoqmQ2vGmEl4vK0jhSdBfug0FlNthLoR2TnbULuR4zo+1qbBWvYeImI2hhWQq5FgdOF/wBs5bFFxshllGVIrfZCAUAeMl4imCd4DI59x5eOsjDKWUrRGUaY21FlIIJBBBBFwQRmCCNOYMfx+JFS9TJHIAqKosrn/wCwAZAk2JXS9yNbBasCLH9xf1ETWw3Hge6NS+TDj7RGoHMC9sxny7ZsNkdIauDuqkVFa+W9cBlYqSOV7eIKmY00yJYbJrOjMaaF7CxFibXsb5f0wYJ+md7wwsbyRj9pLSps7mwA/tC9nMH092ld1wynJVNR+62Q+/1Qm6WgxK5bGX2+qb1U+/UNhf8AIgN724FvpMrtDaxqNdjcDQH1IlRjcUzNcn6W5CRzUykVhXbOp560SsViiwtK5RDZrmHvSsYqK0QnNydsUFinhULHX71iarZXh7DqNjRzmv6AYkUsUhJtv9Xsz4fH4TKUE4mT6bEWINmWxB4giaZNfJ6JUcoGeZLod0pTEoqObVVABztvW4ial275GUEVjNhsZBxFPPhJP35xp1Guv7RcEaWVopcZTW9ySbcF+p+kNaTbufUU8tSeHaZYlRe+6L6DK51GnkZD2nj6dEF6zomRtvEDwQasT2TSil0ZlOUu2Qf/AE1GY9XIXJJ0W3qx5905d0t2n7XEMVyVTurbQBchbyl/0o6ce0RqGFBRDkztk79ij8q9+fdMXi1uxPM/v85tLZh7TomYZt9SMgRna3LiPpGH+zGMK5U3GozEsa6qxvmLgHhx5cyNCOyZfiyq8o37Igvr9jvjq6Rt0Ktb7IiFbdN/szXZh67HjlrpzEk4HGPSJamxUkWNrZjUayOhupK/5lPqIycu7717YIT+Tvm0NprTQuczoo5twH3ynG8ftEu9Soxuzt4WGl+fDymtxu1RULPY7tMMq34uQd5hzsLDznOix4zN8maS4R/Iy9Qk3OpiVhWvDaUJAIiQYpTwP9oGQg2MB17FA2iHPDtgJh0xneANklVyAjsbWLJgZF0qpVgykgjQjIzZbI/ECsihKqCoB+a+61vnMRFIdT2RNAmdl2J0mpYkdQOp3rEMAM7DiCfOWG0sXuUqjixZEchdesFLBT4C/dMR+H+F6qHmS57c+PmJt9t0i6NchVCuxAGbMKbqtzfQb2nHKYaplE7RxjF9K8Y5N8RUz/SQngNwC0p6jMx3mYknUklie8mJOsXK9ErsXh6YJ7rn4ftC3rg3ilyB7o0chM+zd0hJSx++dpNoEmwvxyvoG/e0XSw++m+LdW1xx5fKNqnVC8yfQW+flJuSZ0Rjx+xbksd3iNL65aqecTWo5XGozt2ffpAwJtwcf6rZ3784rD1QTY5Xv4HWG1tBpupeyIGtmPvvjzMGzvY8f3khsOCTYcLleB52PCV7pY8ZtNSJSi4m+fD7lC5tbd3jne9zmb95mGxHvE8Jo6u197DFD7xYWJyO6ufr6yiqi56xv8IRVCbvRD7TERdQ5xAWaMBR4tvCx1GndyjJPKAG0GgToF47hxG6gzy45x2lAT7JAMDRN4LwEETFqtwBxZgPM2jd5Y7Io79emvIlj4DL42gB1nongwlIEi1wAO7+/pLPaj2pVG4Cm55aIfpHMBTCIi8lHpeQ+kRthq5//Gp/sY/OYe2UXRwLjHVjYGcWJQmOuLC3393jJbh3eh+sW7+OVogCJGmybsuqQSvFgbd9r5+UcwzKxZGyJ93v3tD6+cgq1iCMiPu8ViKgLb65G9yO395OUbZWGSl+38Fi4DG+hHUYaWNiLjxkC98zk17Hv7YlsUd4tz17+cQ759h9YRi0OeRMkLiiGBtmPjBVKtwy1HZIm9FAzSjWybm2qZYOiDQ8dCdPGNdUn6fUy26cVE/jay0wqqjBAFAA6igG3jeUSPlNNUZjKxLxLrYQDMiScUOtblfy4TLdM2laZAtBHGjc0YYsjJT2H1jiaRJ4DkLRYgJigYLwoUYhSDOaboVhd+uW4XC+ZBPwmZQ6mdE/DvCWUMdTduHG9vlEwR0EHy+xKjpNU/6TEf8AZf8A2Wk1q4zN5nul+PH8LXVTqjDzyImEjbZx8HMwb0ReC8oYFFoN6IggAotCLQoDAAxD1ESDDgMB1iwYki8K9oAS8biC7u51d2c35sxY+sjs0JomAkGpzEfqvc95+Z+sZVbZmEGtnMtG06BWbP4Q6SxqSKIjMt2xW7DtDY2jTPGIcJiGeNFoUBD9IFiEHEgec6psU7iALYGw8rD6znXR3Dl6oNrhR65fWdEw4AA5gHQevlAZLNZrXPw7b/SUfSd/8CoL36p9ZdK3xI9f3lF0mzoP3HPKNCZzmCCCAAghwoACCHCgAIFaCAxDFiJPZCDWh70Qx9UJ0VvWG9Pd97Lsyv5DSNGs36j5mNkx7DQpnvEMYZMSIAGI4j2iBBGIUzXiYIICBAIIai+XOAGt6JYXqFyNT8NPkfOa2i18uO7ytrfl3iUexUCIgNvdGWug5eMuWcKDmMiPDSAEimxsQeY+VxKTpGP+nf8Ap9RLNHyBuT+w0+Eptvveg4P6fp9+MYGBgggiAEEKHAAQQQQAKCTNl4B69ZKKW3qjWBOgABJY9gAJ8Jssb+HTIgKVrvcAlwiU8+AG8Xv3BvCJtI0ot9GAeC0m7b2XUw1U0am7vABgVN1ZW0K3APAjMA5GQ7QE1QDChmEYwCMNIRhpEAcEEEYgQQQQAEk4BLuo7b+UjGTdk/8AuD74iIZtMPYIOwcM/wAo4cJPKjdJP3kOcq6fuju+Qj9RzzPn2CaXQiYgGRBHH/lylP0gI9k9v0njnqOEsm90ePzlLtv3KncfVIITMhBBBEMEEEEABDgggBqfw9ok4pnH5KTm/IsVUDxBbyM6SyM4UuSM7hRa9uZMw/4XjPEd1D1qTc433vGQydnbh1FEqvsbD1FN0QswALMA97CwDBsmHZ5WlSOiGznRXOGCkgXC1KwAIvcWDAa3zllgfdPjKrCVW9lTzPu8zN3ow8Scmf/Z","5:10"));

        chatAdapter = new ChatAdapter(chatItemList);
        chatRecyclerView.setAdapter(chatAdapter);

        // Load avatar from API
        loadProfileData();

        // Load chat data from API
        loadChatData();
    }

    private void createPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_profile_settings, null);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_options),
                height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        avatarCardView.post(() -> popupWindow.showAsDropDown(avatarCardView, Gravity.AXIS_X_SHIFT, 5, 0));

        setting = popupView.findViewById(R.id.text_setting);
        profile = popupView.findViewById(R.id.text_profile);

        setting.setOnClickListener(v -> gotoSetting());
        profile.setOnClickListener(v -> gotoMyProfile());
    }

    public void gotoSearchUser() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    public void gotoMyProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void gotoSetting() {
        Intent intent = new Intent(this, SettingScreen.class);
        startActivity(intent);
    }

    private void setSelectedButton(Button selectedButton) {
        for (Button button : buttons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.selected_button);
                button.setTextColor(getResources().getColor(R.color.primary_white));
            } else {
                button.setBackgroundResource(R.drawable.unselected_button);
                button.setTextColor(getResources().getColor(R.color.primary_dark));
            }
        }
    }

    private void loadProfileData() {
        // Giả sử bạn có URL của avatar từ API
        String avatarUrl = "https://example.com/path/to/avatar.jpg";

        // Sử dụng Glide để tải và hiển thị ảnh avatar
        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.placeholder_avatar) // Hình ảnh hiển thị khi đang tải
                .error(R.drawable.error_avatar) // Hình ảnh hiển thị khi tải thất bại
                .into(avatarImageView);
    }

    private void loadChatData() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://example.com/path/to/chatdata.json"; // URL của JSON API

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            parseJsonData(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void parseJsonData(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String message = jsonObject.getString("message");
            String time = jsonObject.getString("time");
            String avatarUrl = jsonObject.getString("avatarUrl");
            chatItemList.add(new ChatItem(title, message, time, avatarUrl));
        }
        chatAdapter.notifyDataSetChanged();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}

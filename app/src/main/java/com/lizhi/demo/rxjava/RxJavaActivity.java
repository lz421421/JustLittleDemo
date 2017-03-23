package com.lizhi.demo.rxjava;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.observ.User;
import com.lizhi.demo.utils.LogUtil;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 39157 on 2016/10/25.
 */

public class RxJavaActivity extends BaseActivity {
    TextView btn_subscriber;
    Subscriber subscriber;//观察者 接口的抽象实现类
    Observable observable;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rxjava_activity);
        initData();
    }

    public void initData() {
        btn_subscriber = (TextView) findViewById(R.id.btn_subscriber);
        //观察者  接口
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.log("-------onCompleted----------->");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.log("-------onError----------->");
            }

            @Override
            public void onNext(String s) {
                LogUtil.log("-------onNext----------->" + s);
            }
        };

        subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                //开始分发之前调用 一般做些准备工作比如 数据的清零或重置
                //运行在订阅线程 不是在主线程
                LogUtil.log("-------onStart----------->");

            }

            @Override
            public void onCompleted() {
                LogUtil.log("-------onCompleted----------->");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LogUtil.log("-------onError----------->" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                LogUtil.log("-------onNext----------->" + s);
            }
        };

     /*   observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });*/

        observable = Observable.just("just1", "just2", "just3");
        final Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                LogUtil.log("----Action1--------->" + s);
            }
        };

        btn_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     observable.subscribe(subscriber);
                observable.subscribe(onNextAction);
                fromTest();
                showImgTest();
                mapTest();
                mapTest2();*/
                flatMap();
//                schedukersTest();
            }
        });

    }

    public List<Student> initStudent() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student student = new Student("student" + i);
            List<Course> courses = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Course course = new Course("Course" + j);
                courses.add(course);
            }
            student.courses = courses;
            students.add(student);
        }
        return students;
    }

    public void flatMap() {
        Observable.from(initStudent()).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                LogUtil.log("----------student.name----->" + student.name);
                return Observable.from(student.courses);
            }
        }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {
                LogUtil.log("------------flatMap----course.courseName---->" + course.courseName);
            }
        });
    }

    public void mapTest2() {
        User[] users = new User[]{new User("1"), new User("2"), new User("3"), new User("4")};
        Observable.from(users).map(new Func1<User, String>() {
            @Override
            public String call(User user) {
                return user.name;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.log("---------mapTest2-onCompleted------->");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String name) {
                LogUtil.log("------mapTest2----onNext---name---->" + name);
            }
        });
    }


    public void mapTest() {
        final ImageView img = (ImageView) findViewById(R.id.img_showImg);
        Observable.just("http://img5.imgtn.bdimg.com/it/u=3943177884,1220938491&fm=21&gp=0.jpg").map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String path) {
                //获取网络图片 返回bitmap
                return getBitMapFromNet(path);
            }
        }).subscribeOn(Schedulers.io())//在子线程订阅
                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        img.setImageBitmap(bitmap);
                    }
                });
    }

    public Bitmap getBitMapFromNet(String path) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void schedukersTest() {
        LogUtil.log("---main-->" + Thread.currentThread() + "--->");
        Observable.just(1, 2, 3, 4).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.log("----->" + Thread.currentThread() + "--->" + integer);

            }
        });

        Observable.just(1, 2, 3, 4).subscribeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.log("---main-11111->" + Thread.currentThread() + "--->" + integer);

            }
        });


        Observable.just(1, 2, 3, 4).observeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.log("---main-2222->" + Thread.currentThread() + "--->" + integer);

            }
        });
    }


    public void showImgTest() {
        final int drawableId = R.drawable.goods_sample;
        final ImageView img = (ImageView) findViewById(R.id.img_showImg);
        Observable.unsafeCreate(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {//加载在线程
                LogUtil.log("----showImgTest--->" + Thread.currentThread() + "--->");
             /*   try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                LogUtil.log("----showImgTest--->" + Thread.currentThread() + "--->");
                Drawable drawable = getTheme().getDrawable(drawableId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//表示回调在主线程
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {
                        showToast("----显示完毕");
                        LogUtil.log("----showImgTest--onCompleted->" + Thread.currentThread() + "--->");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtil.log("----showImgTest--onError->" + Thread.currentThread() + "--->");
//                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        LogUtil.log("----showImgTest--onNext->" + Thread.currentThread() + "--->");
                        img.setImageDrawable(drawable);
                    }
                });
    }

    /**
     * 显示数组
     */
    public void fromTest() {
        String[] name = {"名字1", "名字2", "名字3", "名字4"};
        Observable.from(name).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtil.log("-------Observable.from(name).subscribe----->" + s);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscriber != null && subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();//取消订阅  防止内存泄漏 因为 Observable 会持有 Subscriber对象的引用 导致 Observable 销毁不掉
        }
    }
}

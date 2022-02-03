import java.io.*;
//import java.util.*;

public class pointQuery_RangeUpdate {

  public static class SegmentTree {
    int lazy[];
    int ar[];
    SegmentTree(int arr[]) {
      ar = arr;
      lazy = new int[4*ar.length];
      build(1,0,ar.length-1);
    }

    private void build(int node, int start, int end){
      if(start == end){
        lazy[node] = ar[start];
      }else{
        int mid = (start + end)/2;
        build(node*2,start,mid);
        build(node*2+1,mid+1,end);
        lazy[node] = 0;
      }
    }

    void update(int l, int r, int val) {
      update(1,0,ar.length-1,l,r,val);
    }

    private void update(int node, int start, int end, int l, int r, int val){
      if(end < l || start > r)return;
      if(start == end){
        lazy[node] += val;
      }else if(start >= l && end <= r){
        lazy[node] += val;
      }else{
        int mid = (start + end)/2;
        update(node*2,start, mid, l, r,val);
        update(node*2+1, mid+1, end, l, r, val);
      }
    }

    private void propagate(int node,int start, int end){
      if(start == end)return;
      int left = 2*node;
      int right = 2*node+1;
      lazy[left] += lazy[node];
      lazy[right] += lazy[node];
      lazy[node] = 0;
    }

    int query(int ind) {
      return query(1,0,ar.length-1,ind);
    }

    private int query(int node, int start, int end, int idx){
      propagate(node,start,end);
      if(start == end){
        return lazy[node];
      }else{
        int mid = (start + end)/2;
        if(start <= idx && idx <= mid){
          return query(node*2,start,mid,idx);
        }else{
          return query(node*2+1,mid+1,end,idx);
        }
      }
    }

  }

  public static void main(String[] args) throws Exception {
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(read.readLine());
    int arr[] = new int[n];

    for (int i = 0; i < n; i++) {
      arr[i] = Integer.parseInt(read.readLine());
    }

    SegmentTree obj = new SegmentTree(arr);

    int q = Integer.parseInt(read.readLine());

    StringBuilder out = new StringBuilder();
    while (q-- > 0) {
      String[]inp = read.readLine().split(" ");

      int t = Integer.parseInt(inp[0]);

      if (t == 0) {
        int ind = Integer.parseInt(inp[1]);
        long ans = obj.query(ind);
        out.append(ans + "\n");
      } else {
        int l = Integer.parseInt(inp[1]);
        int r = Integer.parseInt(inp[2]);
        int val = Integer.parseInt(inp[3]);
        obj.update(l, r, val);
      }
    }

    System.out.println(out);
  }

}